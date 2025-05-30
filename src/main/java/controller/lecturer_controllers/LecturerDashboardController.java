/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.lecturer_controllers;

import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import controller.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import objects.Lecturer;
import view.LoginView;
import view.lecturer_view.LecturerDashboardView;
import view.lecturer_view.LecturerManageCoursesView;
import view.lecturer_view.LecturerViewDetailsView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Student dashboard related
 * implementations.
 *
 */
public class LecturerDashboardController implements ActionListener {

    private LecturerDashboardView view;
    private Lecturer currentLecturer;

    public LecturerDashboardController(LecturerDashboardView view, Lecturer currentLecturer) {
        this.view = view;
        this.currentLecturer = currentLecturer;
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
                NavigationUtil.newFrame(new LoginView("2"));
                UserController.logOutCurrentUser();
                return;
            }
        }

        switch (command) {
            case "1": //View My Details
                NavigationUtil.newFrame(new LecturerViewDetailsView(currentLecturer));
                break;
            case "2": //Manage My Courses
                NavigationUtil.newFrame(new LecturerManageCoursesView(currentLecturer));
                break;
        }
    }
}
