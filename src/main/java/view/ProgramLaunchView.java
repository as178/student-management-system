/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import concrete_classes.other.GUIConfigUtil;
import controller.ProgramLaunchController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown first when the program is run.
 */
public class ProgramLaunchView extends JFrame {

    public ProgramLaunchView() {

        setTitle("Student Management System");
        setSize(GUIConfigUtil.width, GUIConfigUtil.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //6 rows for 2 labels & 4 buttons, with spacing
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        //Labels
        JLabel textLabel = new JLabel("Welcome to the Student Management System!");
        JLabel textLabel2 = new JLabel("Please pick an option below:");
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel2.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        //Buttons
        JButton studentButton = new JButton("Student");
        JButton lecturerButton = new JButton("Lecturer");
        JButton adminButton = new JButton("Admin");
        JButton exitButton = new JButton("Exit");

        //Styling for the buttons
        JButton[] buttons = {studentButton, lecturerButton, adminButton, exitButton};
        Dimension buttonSize = new Dimension(220, 45);
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 16);

        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            button.setFont(buttonFont);
        }

        //Adding components to main panel
        mainPanel.add(textLabel);
        mainPanel.add(textLabel2);
        mainPanel.add(studentButton);
        mainPanel.add(lecturerButton);
        mainPanel.add(adminButton);
        mainPanel.add(exitButton);

        //Adding main panel to frame
        this.add(mainPanel);

        /*
        Setting up action commands for each button, and giving them all action listeners
        (respective controller).
        */
        ProgramLaunchController controller = new ProgramLaunchController(this);
        studentButton.setActionCommand("1");
        lecturerButton.setActionCommand("2");
        adminButton.setActionCommand("3");
        exitButton.setActionCommand("x");

        for (JButton button : buttons) {
            button.addActionListener(controller);
        }
    }
}
