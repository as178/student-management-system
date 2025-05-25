/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import concrete_classes.admin.Admin;
import controller.admin_controllers.AdminDashboardController;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 */
public class AdminDashboardView extends JFrame {

    private JButton viewDetailsButton, changeuserpasswordButton, logoutButton, exitButton;

    public AdminDashboardView(Admin currentAdmin) {
        setTitle("Student management System: Admin Dashboard");

        JLabel welcomeLabel = new JLabel("Welcome to the Admin Dashboard,");
        JLabel nameLabel = new JLabel(currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");
        JLabel promptLabel = new JLabel("What would you like to do?");

        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        viewDetailsButton = new JButton("View My Details");
        changeuserpasswordButton = new JButton("Change A Users Password");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");

        JButton[] buttons = {viewDetailsButton, changeuserpasswordButton, logoutButton, exitButton};
        Dimension buttonSize = new Dimension(240, 40);
        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        }

        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        mainPanel.add(welcomeLabel);
        mainPanel.add(nameLabel);
        mainPanel.add(promptLabel);
        mainPanel.add(viewDetailsButton);
        mainPanel.add(changeuserpasswordButton);
        mainPanel.add(logoutButton);
        mainPanel.add(exitButton);

        this.add(mainPanel);

        /*
        Setting up action commands for each button, and giving them all action listeners
        (respective controller).
         */
        AdminDashboardController controller = new AdminDashboardController(this, currentAdmin);
        viewDetailsButton.setActionCommand("1");
        changeuserpasswordButton.setActionCommand("2");
        logoutButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        for (JButton button : buttons) {
            button.addActionListener(controller);
        }
    }
}
