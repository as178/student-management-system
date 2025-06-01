/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

import controller.UserController;
import java.awt.*;
import javax.swing.*;
import objects.Admin;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when an Admin wishes to search up a user that they want
 * to modify the password for.
 *
 * Controller: AdminLoadUserController
 *
 */
public class AdminLoadUserView extends JFrame {

    private JButton searchButton, backButton, exitButton;
    private JTextField userIdField;

    public AdminLoadUserView(Admin currentAdmin) {
        setTitle("Student Management System: User Look-Up");

        //Initial styling for main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Title label styling
        JLabel titleLabel = new JLabel("Please enter a User's ID below:");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //Form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 15, 15));

        //Adding field to form panel + styling
        userIdField = new JTextField();
        userIdField.setPreferredSize(new Dimension(200, 30)); //width 200, height 30
        formPanel.add(userIdField);

        //Buttons panel + buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        searchButton = new JButton("Search");
        backButton = new JButton("Back");
        exitButton = new JButton("Exit");

        //Buttons styling and config
        JButton[] buttons = {searchButton, backButton, exitButton};
        AdminLoadUserController controller = new AdminLoadUserController(this, (Admin) UserController.getCurrentUser());

        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            buttonPanel.add(button);
            button.addActionListener(controller); //controller will handle events
        }

        //Set action commands for controller
        searchButton.setActionCommand("1");
        backButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        //Adding components to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(formPanel);
        mainPanel.add(buttonPanel);

        //Wrapper class for centering main panel
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        //Adding wrapper to this frame
        this.add(wrapper);
    }

    /*
    Small helper method for retrieving admin's
    input from the text field.
     */
    public String getUserIdField() {
        return userIdField.getText();
    }
}
