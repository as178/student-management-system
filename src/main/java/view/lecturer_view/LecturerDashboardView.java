/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.lecturer_view;

import controller.lecturer_controllers.LecturerDashboardController;
import javax.swing.*;
import java.awt.*;
import objects.Lecturer;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a Lecturer logs into the program.
 * 
 * Controller: LecturerDashboardController
 *
 */
public class LecturerDashboardView extends JFrame {

    private JButton viewDetailsButton, manageCoursesButton, logoutButton, exitButton;

    public LecturerDashboardView(Lecturer currentLecturer) {
        setTitle("Student Management System: Lecturer Dashboard");

        //Main panel config
        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        //Initial text labels to greet the student
        JLabel welcomeLabel = new JLabel("Welcome to the Lecturer Dashboard,");
        JLabel nameLabel = new JLabel(currentLecturer.getFirstName() + " " + currentLecturer.getLastName() + "!");
        JLabel promptLabel = new JLabel("What would you like to do?");

        welcomeLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 19));
        promptLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));

        //Center the text labels
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Adding text labels to main panel
        mainPanel.add(welcomeLabel);
        mainPanel.add(nameLabel);
        mainPanel.add(promptLabel);

        //Buttons, config + styling
        viewDetailsButton = new JButton("View My Details");
        manageCoursesButton = new JButton("Manage My Courses");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");

        JButton[] buttons = {viewDetailsButton, manageCoursesButton, logoutButton, exitButton};
        LecturerDashboardController controller = new LecturerDashboardController(this, currentLecturer);
        Dimension buttonSize = new Dimension(240, 40);

        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            button.setFont(new Font("Monospaced", Font.BOLD, 15));
            button.addActionListener(controller);
            mainPanel.add(button);
        }

        //Action commands for controller
        viewDetailsButton.setActionCommand("1");
        manageCoursesButton.setActionCommand("2");
        logoutButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        this.add(mainPanel);
    }
}
