package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ChooseScreen extends JFrame {
    private JComboBox<String> vehicleComboBox;
    private JButton nextPageButton;
    private JButton backButton;

    public ChooseScreen() {
        super("Vehicle Selection");

        // Create vehicle selection list
        vehicleComboBox = new JComboBox<>();
        vehicleComboBox.setBounds(100, 50, 100, 30);
        add(vehicleComboBox);

        // Add vehicles to the combo box
        for (Car car : Client.getCompany().getAvailableCars()) {
            vehicleComboBox.addItem(car.getMake() + " " + car.getModel());
        }

        // Create next page button
        nextPageButton = new JButton("Next");
        nextPageButton.setBounds(100, 100, 100, 30);
        add(nextPageButton);

        // Add action listener to the next button
        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedCarIndex = vehicleComboBox.getSelectedIndex();
                    new RentScreen(selectedCarIndex); // open rent screen
                    dispose(); // close current screen
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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

        // Set the window properties
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
