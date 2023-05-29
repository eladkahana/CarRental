package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.util.ArrayList;

public class RentScreen extends JFrame  implements ActionListener {

    private JLabel timerLabel;
    private Timer timer;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton confirmButton;
    private JButton cancelButton;


    private int selectedCar;

    public RentScreen(int selectedCar) throws IOException, ClassNotFoundException {
        super("Rent a Car");

        this.selectedCar = selectedCar;

        // Create timer label
        timerLabel = new JLabel("Time Remaining: 5:00");
        timerLabel.setBounds(100, 10, 1000, 30);
        add(timerLabel);

        // Create timer
        timer = new Timer(1000, new ActionListener() {
            int seconds = 300;

            public void actionPerformed(ActionEvent e) {
                seconds--;

                if (seconds >= 0) {
                    int minutes = seconds / 60;
                    int sec = seconds % 60;
                    timerLabel.setText("Time Remaining: " + minutes + ":" + String.format("%02d", sec));
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Time is up!");
                    dispose(); // close current screen
                    new MainScreen(); // open main screen
                }
            }
        });

        // Start the timer
        timer.start();




        Client.reserveCar(selectedCar);


        // Create name label and text field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 100, 150, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 100, 150, 30);
        add(nameField);

// Create phone number label and text field
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(10, 150, 150, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 150, 150, 30);
        add(phoneField);


        // Create confirm button
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(50, 200, 100, 30);
        add(confirmButton);

        // Add action listener to confirm button
        confirmButton.addActionListener(this);

        // Create cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 200, 100, 30);
        add(cancelButton);

        // Add action listener to cancel button
        cancelButton.addActionListener(this);

        // Set frame properties
        setSize(350, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String carDetails = Client.getCompany().getAvailableCars().get(selectedCar).getMake() + Client.getCompany().getAvailableCars().get(selectedCar).getModel();
            // Perform validation on name and phone number
            if (name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all details.");
                return;
            }


            //update the car list
            try {
                Client.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }


            try {
                Client.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

                try {
                    //rent the car
                    Client.rentCar(selectedCar, new Customer(name, phone));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                // Display confirmation message
                JOptionPane.showMessageDialog(null, "You have rented " + carDetails + ".\nYour name: " + name + "\nPhone number: " + phone);
                timer.stop();
                dispose(); // close current screen
                new MainScreen(); // open main screen



        } else if (e.getSource() == cancelButton) {
            // Display confirmation message
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Cancel Rental", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    Client.cancel(selectedCar);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                timer.stop();
                dispose(); // close current screen
                new MainScreen(); // open main screen
            }
        }
    }
}

