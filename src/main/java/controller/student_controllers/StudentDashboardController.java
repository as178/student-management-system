/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import objects.Student;
import controller.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.LoginView;
import view.student_view.StudentAcademicDetailsView;
import view.student_view.StudentDashboardView;
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

    private StudentDashboardView view;
    private Student currentStudent;

    public StudentDashboardController(StudentDashboardView view, Student currentStudent) {
        this.view = view;
        this.currentStudent = currentStudent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("x".equalsIgnoreCase(command)) {
            NavigationUtil.exitProgram();
            return;
        }

        if ("b".equalsIgnoreCase(command)) {

            int confirmation = PopUpUtil.displayConfirmInfo("Are you sure you want to logout?");

            if (confirmation == JOptionPane.YES_OPTION) {
                NavigationUtil.newFrame(new LoginView("1"));
                UserController.logOutCurrentUser();
                return;
            }
        }

        switch (command) {
            case "1": //View My Details
                NavigationUtil.newFrame(new StudentViewDetailsView(currentStudent));
                break;
            case "2": //View My Academic Details
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;
        }
    }
}
