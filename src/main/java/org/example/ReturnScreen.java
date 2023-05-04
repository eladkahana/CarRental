package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class ReturnScreen  extends JFrame  implements ActionListener {

    private JComboBox<String> vehicleList;
    private JButton confirmButton;
    private JButton backButton;

    public ReturnScreen() {
        super("Return a Car");

        // Create vehicle selection list
        ArrayList<Car> vehicles = Client.getCompany().getRentedCars();
        vehicleList = new JComboBox<>();
        vehicleList.setBounds(100, 50, 100, 30);
        add(vehicleList);

        // Add vehicles to the combo box
        for (Car car : vehicles) {
            vehicleList.addItem(car.getMake() + " " + car.getModel());
        }

        // Create confirm button
        confirmButton = new JButton("Confirm Return");
        confirmButton.setBounds(100, 100, 150, 30);
        add(confirmButton);

        // Add action listener to confirm button
        confirmButton.addActionListener(this);

        // Create back button
        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 80, 30);
        add(backButton);

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // close current screen
                new MainScreen(); // open previous screen
            }
        });

        // Set frame properties
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            int returnedCar = vehicleList.getSelectedIndex();
            String carDetails = Client.getCompany().getRentedCars().get(returnedCar).getMake() + Client.getCompany().getRentedCars().get(returnedCar).getModel();

            try {
                Client.returnCar(returnedCar);

            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            // Display confirmation message
            JOptionPane.showMessageDialog(null, "You have returned " + carDetails + ".");
            dispose(); // close current screen
            new MainScreen(); // open main screen
        }
    }
}
