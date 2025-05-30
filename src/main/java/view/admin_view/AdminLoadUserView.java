/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

import controller.admin_controllers.AdminLoadUserControllers;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import objects.Admin;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 */
public class AdminLoadUserView extends JFrame {

    private JButton searchButton, backButton, exitButton;
    private JTextField userIdField;

    public AdminLoadUserView(Admin currentAdmin) {
        setTitle("Student Management System: Admin Dashboard");

        JLabel promptLabel = new JLabel("Enter a UserID");
        promptLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        userIdField = new JTextField(10);
        searchButton = new JButton("Search");
        backButton = new JButton("Back");
        exitButton = new JButton("Exit");

        JButton[] buttons = {searchButton, backButton, exitButton};
        Dimension buttonSize = new Dimension(240, 40);
        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            button.setFont(new Font("Monospaced", Font.PLAIN, 14));
        }

        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        mainPanel.add(promptLabel);
        mainPanel.add(userIdField);
        mainPanel.add(searchButton);
        mainPanel.add(backButton);
        mainPanel.add(exitButton);

        this.add(mainPanel);

        /*
        Setting up action commands for each button, and giving them all action listeners
        (respective controller).
         */
        AdminLoadUserControllers controller = new AdminLoadUserControllers(this, currentAdmin);
        searchButton.setActionCommand("1");
        backButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        for (JButton button : buttons) {
            button.addActionListener(controller);
        }
    }

    public JTextField getUserIdField() {
        return userIdField;
    }
}