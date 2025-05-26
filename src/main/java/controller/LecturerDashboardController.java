/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import concrete_classes.lecturer.Lecturer;
import concrete_classes.other.NavigationUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.LecturerDashboardView;
import view.LecturerViewDetailsView;
import view.LoginView;

/**
 *
 * @author williamniven
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

            int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                NavigationUtil.newFrame(new LoginView("1"));
                UserController.logOutCurrentUser();
                return;
            }
        }

        switch (command) {
            case "1":
                NavigationUtil.newFrame(new LecturerViewDetailsView(currentLecturer));
                break;
            case "2":
                //Modify courses
        }
    }
}