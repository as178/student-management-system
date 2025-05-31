/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

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
import objects.Admin;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 */
public class AdminDashboardView extends JFrame {

    private JButton viewDetailsButton, changeUserPasswordButton, createNewUserButton,logoutButton, exitButton;

    public AdminDashboardView(Admin currentAdmin) {
        setTitle("Student management System: Admin Dashboard");

        JLabel welcomeLabel = new JLabel("Welcome to the Admin Dashboard,");
        JLabel nameLabel = new JLabel(currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");
        JLabel promptLabel = new JLabel("What would you like to do?");

        welcomeLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        promptLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));

        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        viewDetailsButton = new JButton("View My Details");
        changeUserPasswordButton = new JButton("Change A Users Password");
        createNewUserButton = new JButton("Create New User");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");

        JButton[] buttons = {viewDetailsButton, changeUserPasswordButton, createNewUserButton,logoutButton, exitButton};
        Dimension buttonSize = new Dimension(240, 40);
        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            button.setFont(new Font("Monospaced", Font.PLAIN, 14));
        }

        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        mainPanel.add(welcomeLabel);
        mainPanel.add(nameLabel);
        mainPanel.add(promptLabel);
        mainPanel.add(viewDetailsButton);
        mainPanel.add(changeUserPasswordButton);
        mainPanel.add(createNewUserButton);
        mainPanel.add(logoutButton);
        mainPanel.add(exitButton);

        this.add(mainPanel);

        /*
        Setting up action commands for each button, and giving them all action listeners
        (respective controller).
         */
        AdminDashboardController controller = new AdminDashboardController(this, currentAdmin);
        viewDetailsButton.setActionCommand("1");
        changeUserPasswordButton.setActionCommand("2");
        createNewUserButton.setActionCommand("3");
        logoutButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        for (JButton button : buttons) {
            button.addActionListener(controller);
        }
    }
}