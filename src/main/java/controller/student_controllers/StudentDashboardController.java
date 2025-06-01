/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
import objects.Student;
import controller.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.LoginView;
import view.student_view.StudentAcademicDetailsView;
import view.student_view.StudentViewDetailsView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Student dashboard related
 * implementations.
 *
 */
public class StudentDashboardController implements ActionListener {

    private Student currentStudent;

    public StudentDashboardController(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        switch (command) {
            case "1": //dashboard for viewing and modifying personal information
                NavigationUtil.newFrame(new StudentViewDetailsView(currentStudent));
                break;
            case "2": //dashboard for viewing academic details
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;
            case "b": //going back will log the student out
                
                //confirmation pop up 
                int confirmation = PopUpUtil.displayConfirmInfo("Are you sure you want to logout?");

                //if confirmed, go back to the login window for students + logout current student
                if (confirmation == JOptionPane.YES_OPTION) {
                    NavigationUtil.newFrame(new LoginView("1"));
                    UserController.logOutCurrentUser();
                    return;
                }
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }
}
