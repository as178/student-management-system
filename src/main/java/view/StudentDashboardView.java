/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import concrete_classes.student.Student;
import controller.student_controllers.StudentDashboardController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a Student logs into the program.
 */
public class StudentDashboardView extends JFrame {

    private JButton viewDetailsButton, viewAcademicButton, logoutButton, exitButton;

    public StudentDashboardView(Student currentStudent) {
        setTitle("Student Management System: Student Dashboard");

        JLabel welcomeLabel = new JLabel("Welcome to the Student Dashboard,");
        JLabel nameLabel = new JLabel(currentStudent.getFirstName() + " " + currentStudent.getLastName() + "!");
        JLabel promptLabel = new JLabel("What would you like to do?");

        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        viewDetailsButton = new JButton("View My Details");
        viewAcademicButton = new JButton("View My Academic Details");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");

        JButton[] buttons = {viewDetailsButton, viewAcademicButton, logoutButton, exitButton};
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
        mainPanel.add(viewAcademicButton);
        mainPanel.add(logoutButton);
        mainPanel.add(exitButton);

        this.add(mainPanel);

        /*
        Setting up action commands for each button, and giving them all action listeners
        (respective controller).
         */
        StudentDashboardController controller = new StudentDashboardController(this, currentStudent);
        viewDetailsButton.setActionCommand("1");
        viewAcademicButton.setActionCommand("2");
        logoutButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        for (JButton button : buttons) {
            button.addActionListener(controller);
        }
    }
}
