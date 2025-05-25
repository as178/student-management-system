/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import concrete_classes.other.GUIConfigUtil;
import controller.LoginController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a user chooses to login into the program.
 */
public class LoginView extends JFrame {

    private JTextField usernameId;
    private JPasswordField password;
    private JButton loginButton, backButton, exitButton;

    public LoginView(String roleCommand) {
        setTitle("Student Management System: Login");
        setSize(GUIConfigUtil.width, GUIConfigUtil.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Initial styling for main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Title label styling
        JLabel titleLabel = new JLabel("Please enter your credentials below:");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //Form panel, input text fields
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        formPanel.setMaximumSize(new Dimension(400, 100));

        JLabel usernameLabel = new JLabel("Username (ID):");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        usernameId = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        password = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameId);
        formPanel.add(passwordLabel);
        formPanel.add(password);

        //Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        buttonPanel.setMaximumSize(new Dimension(400, 40));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        loginButton = new JButton("Login");
        backButton = new JButton("Back");
        exitButton = new JButton("Exit");

        //Buttons styling
        JButton[] buttons = {loginButton, backButton, exitButton};
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(120, 40));
            button.setFont(new Font("SansSerif", Font.PLAIN, 14));
            buttonPanel.add(button);
        }

        //Adding components to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // spacing
        mainPanel.add(buttonPanel);

        //Adding main panel to this frame
        this.add(mainPanel);
        
        /*
        Setting up action commands for each button, and giving them all action listeners
        (respective controller).
        */
        LoginController controller = new LoginController(this);
        loginButton.setActionCommand(roleCommand); //user's type, who they want to login as 
        backButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        for (JButton button : buttons) {
            button.addActionListener(controller);
        }
    }

    public String getUsername() {
        return usernameId.getText().trim();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }
}
