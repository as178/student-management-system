/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import abstract_classes.User;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import objects.Admin;
import objects.Lecturer;
import objects.Student;
import view.LoginView;
import view.ProgramLaunchView;
import view.admin_view.AdminDashboardView;
import view.lecturer_view.LecturerDashboardView;
import view.student_view.StudentDashboardView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any initial login related logic which
 * happens on the LoginView screen.
 *
 */
public class LoginController implements ActionListener {

    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("x".equalsIgnoreCase(command)) {
            NavigationUtil.exitProgram();
            return;
        }

        if ("b".equalsIgnoreCase(command)) {
            NavigationUtil.newFrame(new ProgramLaunchView());
            return;
        }

        String username = view.getUsername();
        String password = view.getPassword();

        if (UserController.getCurrentUsers().containsKey(username)) {

            User user = UserController.getCurrentUsers().get(username);

            if (user.getPassword().equals(password)) {
                UserController.setCurrentUser(user);

                switch (command) {
                    case "1":
                        NavigationUtil.newFrame(new StudentDashboardView((Student) UserController.getCurrentUser()));
                        break;
                    case "2":
                        NavigationUtil.newFrame(new LecturerDashboardView((Lecturer) UserController.getCurrentUser()));
                        break;
                    case "3":
                        NavigationUtil.newFrame(new AdminDashboardView((Admin) UserController.getCurrentUser()));
                        break;
                }
            } else {
                PopUpUtil.displayError("Incorrect credentials, please try again.");
            }
        } else {
            PopUpUtil.displayError("Invalid user, please try again.");
        }
    }
}
