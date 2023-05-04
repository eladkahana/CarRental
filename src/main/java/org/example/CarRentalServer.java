package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarRentalServer {

    public static void main(String[] args) throws IOException {

        // Initialize a list of available cars for the rental company
        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(new Car("Toyota", "Corolla"));
        cars.add(new Car("Tesla", "Model 3"));
        cars.add(new Car("Kia", "Picanto"));

        // Create a new car rental company instance with the list of available cars
        CarRental company = new CarRental(cars);

        // Create a lock object to synchronize access to the car rental company object
        Lock lock = new ReentrantLock();

        try {
            // Create a new server socket to listen for client requests on port 5000
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started, waiting for clients...");
            while (true) {
                // Accept incoming client connections and create a new thread to handle the client request
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new client handler thread with the client socket, car rental company, and lock objects
                ClientHandler clientHandler = new ClientHandler(clientSocket, company, lock);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class to handle client requests in a separate thread
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
                // Read the client command from the input stream
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                String command = (String) in.readObject();
                System.out.println("Command received: " + command);

                // Handle the client command based on its type
                switch (command) {
                    case "start":
                        // If the command is "start", serialize and send the car rental company object to the client
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                        out.writeObject(company);
                        out.flush();
                        break;
                    case "reserve":
                        // If the command is "reserve", read the car index to reserve from the input stream
                        // Lock the car rental company object and reserve the selected car for the client
                        int carToReserve = (int) in.readObject();
                        lock.lock();
                        try {
                            company.reserve(company.getAvailableCars().get(carToReserve));
                        } finally {
                            lock.unlock();
                        }
                        break;
                    case "rent":
                        // If the command is "rent", read the car index and customer object from the input stream
                        // Lock the car rental company object and rent the selected car to the client
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
                        int carToReturn = (int) in.readObject(); // read the index of the car to return from the client
                        lock.lock(); // acquire the lock to avoid concurrency issues
                        try {
                            company.carReturn(company.getRentedCars().get(carToReturn)); // return the specified car
                        } finally {
                            lock.unlock(); // release the lock
                        }
                        break;
                    case "update":
                        // If the command is "update", serialize and send the car rental company object to the client
                        out = new ObjectOutputStream(clientSocket.getOutputStream());
                        out.writeObject(company);
                        out.flush();
                        break;
                    case "cancel":
                        int carToCancel = (int) in.readObject(); // read the index of the car to return from the client
                        lock.lock(); // acquire the lock to avoid concurrency issues
                        try {
                            company.cancel(company.getAvailableCars().get(carToCancel)); // cancel the specified car
                        } finally {
                            lock.unlock(); // release the lock
                        }
                        break;
                    default:
                        System.out.println("Invalid command: " + command);
                        break;
                }

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(company); // serialize and send the updated company object to the client
                out.flush(); // flush the output stream to ensure all data is sent






            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}