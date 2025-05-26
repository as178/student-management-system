/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.LoginController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a user chooses to login into the program.
 * 
 * Controller: LoginController
 *
 */
public class LoginView extends JFrame {

    private JTextField usernameId;
    private JPasswordField password;
    private JButton loginButton, backButton, exitButton;

    public LoginView(String roleCommand) {
        setTitle("Student Management System: Login");

        //Initial styling for main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Title label styling
        JLabel titleLabel = new JLabel("Please enter your credentials below:");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 19));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //Form panel, input text fields for username and password
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 15, 15));

        JLabel usernameLabel = new JLabel("Username (ID):");
        usernameLabel.setFont(new Font("Monospaced", Font.PLAIN, 17));
        usernameId = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Monospaced", Font.PLAIN, 17));
        password = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameId);
        formPanel.add(passwordLabel);
        formPanel.add(password);

        //Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        loginButton = new JButton("Login");
        backButton = new JButton("Back");
        exitButton = new JButton("Exit");

        //Buttons styling and config
        JButton[] buttons = {loginButton, backButton, exitButton};
        LoginController controller = new LoginController(this);

        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            buttonPanel.add(button);
            button.addActionListener(controller); //controller will handle events
        }

        //Set action commands for controller
        loginButton.setActionCommand(roleCommand); //user's type, who they want to login as 
        backButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        //Adding components to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(35)); //spacing
        mainPanel.add(buttonPanel);

        //Wrapper class for centering main panel
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        //Adding wrapper to this frame
        this.add(wrapper);        
    }

    /*
    Methods for retrieving user input from text fields.
     */
    public String getUsername() {
        return usernameId.getText().trim();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }
}
