package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    // The static socket and CarRental instance variables
    private static Socket socket;
    private static CarRental company;

    // The main method is the entry point of the program
    public static void main(String[] args) {

        try {
            // Connect to the server on the local machine at port 5000
            socket = new Socket("localhost", 5000);
            // Print a message indicating that the connection was successful
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            // Send a "start" command to the server to initiate the communication
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject("start");
            out.flush();

            // Read a CarRental object from the server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            company = (CarRental) in.readObject();

            // Close the streams and the socket
            in.close();
            out.close();
            socket.close();

            // Create a new MainScreen object to display the car rental company
            new MainScreen();

        } catch (IOException e) {
            // Print the stack trace if an I/O error occurs
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // Throw a runtime exception if the CarRental class is not found
            throw new RuntimeException(e);
        }
    }

    // The getCompany method returns the CarRental object obtained from the server
    public static CarRental getCompany() {
        return company;
    }

    public static void cancel(int carIndex) throws IOException, ClassNotFoundException {
        // Connect to the server on the local machine at port 5000
        socket = new Socket("localhost", 5000);
        // Print a message indicating that the connection was successful
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        // Send a "cancel" command to the server

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("cancel");
        out.writeObject(carIndex);
        out.flush();

        // Read a CarRental object from the server
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();
    }


    public static void update() throws IOException, ClassNotFoundException {
        // Connect to the server on the local machine at port 5000
        socket = new Socket("localhost", 5000);
        // Print a message indicating that the connection was successful
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        // Send an "update" command to the server

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("update");
        out.flush();

        // Read a CarRental object from the server
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();
    }

    // The reserveCar method reserves a car at the specified index
    public static void reserveCar(int carIndex) throws IOException, ClassNotFoundException {
        // Connect to the server on the local machine at port 5000
        socket = new Socket("localhost", 5000);
        // Print a message indicating that the connection was successful
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        // Send a "reserve" command and the car index to the server
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("reserve");
        out.writeObject(carIndex);
        out.flush();

        // Read a new CarRental object from the server
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();

        // Close the streams and the socket
        in.close();
        out.close();
        socket.close();
    }

    // The rentCar method rents a car at the specified index to a customer
    public static void rentCar(int carIndex, Customer customer) throws IOException, ClassNotFoundException {
        // Connect to the server on the local machine at port 5000
        socket = new Socket("localhost", 5000);
        // Print a message indicating that the connection was successful
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        // Send a "rent" command, the car index, and the customer object to the server
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("rent");
        out.writeObject(carIndex);
        out.writeObject(customer);
        out.flush();

        // Read a new CarRental object from the server
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();

        // Close the streams and the socket
        in.close();
        out.close();
        socket.close();
    }

// The returnCar method returns a car at the specified index

    public static void returnCar(int carIndex) throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", 5000);
        System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("return");  // send a string message to server indicating return car operation
        out.writeObject(carIndex);  // send the car index to be returned
        out.flush();  // flush the output stream

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        company = (CarRental) in.readObject();  // read the updated car rental company object from server

        // Close the streams and socket
        in.close();
        out.close();
        socket.close();  // close the socket

    }
}