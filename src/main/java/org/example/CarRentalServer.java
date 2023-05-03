package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarRentalServer {

    public static void main(String[] args) throws IOException {

        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(new Car(1,"Toyota", "Corolla"));
        cars.add(new Car(2,"Tesla", "Model 3"));
        cars.add(new Car(3,"Kia", "Picanto"));
        CarRental company = new CarRental(cars);

        Lock lock = new ReentrantLock();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started, waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread to handle the client request
                ClientHandler clientHandler = new ClientHandler(clientSocket, company, lock);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final CarRental company;
        private final Lock lock;

        public ClientHandler(Socket clientSocket, CarRental company, Lock lock) {
            this.clientSocket = clientSocket;
            this.company = company;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                // Deserialize the object from the socket input stream
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                String command = (String) in.readObject();
                System.out.println("Command received: " + command);

                // Handle the client request
                switch (command) {
                    case "start":
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                        out.writeObject(company);
                        out.flush();
                        break;
                    case "reserve":
                        int carToReserve = (int) in.readObject();
                        lock.lock();
                        try {

                            company.reserve(company.getAvailableCars().get(carToReserve));
                        } finally {
                            lock.unlock();
                        }
                        break;
                    case "rent":
                        int carToRent = (int) in.readObject();
                        Customer customer = (Customer) in.readObject();
                        lock.lock();
                        try {
                            company.rent(company.getAvailableCars().get(carToRent), customer);
                        } finally {
                            lock.unlock();
                        }
                        break;
                    case "return":
                        int carToReturn = (int) in.readObject();
                        lock.lock();
                        try {
                            company.carReturn(company.getRentedCars().get(carToReturn));
                        } finally {
                            lock.unlock();
                        }
                        break;
                    default:
                        System.out.println("Invalid command: " + command);
                        break;
                }

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(company);
                out.flush();




            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
