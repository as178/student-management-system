/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ProgramLaunchController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown first when the program is run. It allows the
 * user to pick which type of user they wish to login as.
 * 
 * Controller: ProgramLaunchController
 * 
 */
public class ProgramLaunchView extends JFrame {

    public ProgramLaunchView() {
        setTitle("Student Management System");

        //6 rows for 2 labels & 4 buttons, with spacing
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        //Labels
        JLabel textLabel = new JLabel("Welcome to the Student Management System!");
        JLabel textLabel2 = new JLabel("Please pick an option below:");
        textLabel.setFont(new Font("Monospaced", Font.BOLD, 19));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel2.setFont(new Font("Monospaced", Font.PLAIN, 17));
        textLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        //Adding labels to main panel
        mainPanel.add(textLabel);
        mainPanel.add(textLabel2);
        
        //Buttons
        JButton studentButton = new JButton("Student");
        JButton lecturerButton = new JButton("Lecturer");
        JButton adminButton = new JButton("Admin");
        JButton exitButton = new JButton("Exit");

        //Styling  + config for the buttons
        JButton[] buttons = {studentButton, lecturerButton, adminButton, exitButton};
        ProgramLaunchController controller = new ProgramLaunchController(this);

        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 16));
            button.setPreferredSize(new Dimension(220, 45));
            button.addActionListener(controller);
            mainPanel.add(button);
        }

        //Action commands for controller to handle
        studentButton.setActionCommand("1");
        lecturerButton.setActionCommand("2");
        adminButton.setActionCommand("3");
        exitButton.setActionCommand("x");

        //Adding main panel to frame
        this.add(mainPanel);
    }
}