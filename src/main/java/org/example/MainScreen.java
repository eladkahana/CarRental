package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class MainScreen  extends JFrame implements ActionListener {

    private JButton rentButton;
    private JButton returnButton;


    public MainScreen() {


        super("Car Rental System");




        // Create two buttons
        rentButton = new JButton("Rent");
        returnButton = new JButton("Return");

        // Set button positions
        rentButton.setBounds(100, 50, 100, 30);
        returnButton.setBounds(100, 100, 100, 30);

        // Add buttons to frame
        add(rentButton);
        add(returnButton);

        // Add action listeners to buttons
        rentButton.addActionListener(this);
        returnButton.addActionListener(this);

        // Set frame properties
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rentButton) {
            dispose(); // close current screen
            try {
                Client.update();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            new ChooseScreen(); // open rent screen
        } else if (e.getSource() == returnButton) {
            dispose(); // close current screen
            new ReturnScreen(); // open return screen
        }
    }
}
