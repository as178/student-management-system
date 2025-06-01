/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

import controller.admin_controllers.AdminDashboardController;
import java.awt.*;
import javax.swing.*;
import objects.Admin;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when an Admin logs into the program.
 *
 * Controller: AdminDashboardController
 *
 */
public class AdminDashboardView extends JFrame {

    private JButton viewDetailsButton, changeUserPasswordButton, registerNewUserButton,logoutButton, exitButton;

    public AdminDashboardView(Admin currentAdmin) {
        setTitle("Student management System: Admin Dashboard");

        //Main panel config
        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        //Initial text labels to greet the admin
        JLabel welcomeLabel = new JLabel("Welcome to the Admin Dashboard,");
        JLabel nameLabel = new JLabel(currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!");
        JLabel promptLabel = new JLabel("What would you like to do?");

        welcomeLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 19));
        promptLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));

        //Centering the text labels + adding them to main panel
        JLabel[] jlabels = {welcomeLabel, nameLabel, promptLabel};
        for (JLabel label : jlabels) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(label);
        }

        //Buttons, config + styling
        viewDetailsButton = new JButton("View My Details");
        changeUserPasswordButton = new JButton("Change a User's Password");
        registerNewUserButton = new JButton("Register a New User");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");

        JButton[] buttons = {viewDetailsButton, changeUserPasswordButton, registerNewUserButton, logoutButton, exitButton};
        AdminDashboardController controller = new AdminDashboardController(currentAdmin);

        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(240, 40));
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            button.addActionListener(controller);
            mainPanel.add(button);
        }

        //Action commands for controller to handle
        viewDetailsButton.setActionCommand("1");
        changeUserPasswordButton.setActionCommand("2");
        registerNewUserButton.setActionCommand("3");
        logoutButton.setActionCommand("b");
        exitButton.setActionCommand("x");
        
        this.add(mainPanel);
    }
}
