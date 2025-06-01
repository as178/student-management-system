/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.student_view;

import objects.Student;
import controller.student_controllers.StudentDashboardController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a Student logs into the program.
 * 
 * Controller: StudentDashboardController
 *
 */
public class StudentDashboardView extends JFrame {

    private JButton viewDetailsButton, viewAcademicButton, logoutButton, exitButton;

    public StudentDashboardView(Student currentStudent) {
        setTitle("Student Management System: Student Dashboard");

        //Main panel config
        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        //Initial text labels to greet the student
        JLabel welcomeLabel = new JLabel("Welcome to the Student Dashboard,");
        JLabel nameLabel = new JLabel(currentStudent.getFirstName() + " " + currentStudent.getLastName() + "!");
        JLabel promptLabel = new JLabel("What would you like to do?");

        welcomeLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 19));
        promptLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));

        //Centering the text labels + adding them to main panel
        JLabel[] jlabels = {welcomeLabel, nameLabel, promptLabel};
        for (JLabel label : jlabels){
            label.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(label);
        }

        //Buttons, config + styling
        viewDetailsButton = new JButton("View My Details");
        viewAcademicButton = new JButton("View My Academic Details");
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Exit");

        JButton[] buttons = {viewDetailsButton, viewAcademicButton, logoutButton, exitButton};
        StudentDashboardController controller = new StudentDashboardController(currentStudent);

        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(240, 40));
            button.setFont(new Font("Monospaced", Font.BOLD, 15));
            button.addActionListener(controller);
            mainPanel.add(button);
        }

        //Action commands for controller
        viewDetailsButton.setActionCommand("1");
        viewAcademicButton.setActionCommand("2");
        logoutButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        this.add(mainPanel);
    }
}
