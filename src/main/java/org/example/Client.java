package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private static Socket socket;
    private static CarRental company;

    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            // Read the object from the server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject("start");
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            company = (CarRental) in.readObject();

            // Close the streams and socket
            in.close();
            out.close();
            socket.close();

            new MainScreen();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static CarRental getCompany() {
        return company;
    }

    public static void reserve(int carIndex) throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", 5000);
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("reserve");
        out.writeObject(carIndex);
        out.flush();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();

        // Close the streams and socket
        in.close();
        out.close();
        socket.close();
    }

    public static void rent(int carIndex, Customer customer) throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", 5000);
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("rent");
        out.writeObject(carIndex);
        out.writeObject(customer);
        out.flush();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();

        // Close the streams and socket
        in.close();
        out.close();
        socket.close();

    }

    public static void CarReturn(int carIndex) throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", 5000);
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("return");
        out.writeObject(carIndex);
        out.flush();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();

        // Close the streams and socket
        in.close();
        out.close();
        socket.close();
    }
}
