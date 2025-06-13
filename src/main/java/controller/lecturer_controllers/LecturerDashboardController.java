/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.lecturer_controllers;

import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
import controller.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import objects.Lecturer;
import view.LoginView;
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

    private Lecturer currentLecturer;

    public LecturerDashboardController(Lecturer currentLecturer) {
        this.currentLecturer = currentLecturer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "1": //dashboard for viewing and modifying personal information
                NavigationUtil.newFrame(new LecturerViewDetailsView(currentLecturer));
                break;
                
            case "2": //dashboard for managing the courses the lecturer teaches
                NavigationUtil.newFrame(new LecturerManageCoursesView(currentLecturer));
                break;
                
            case "b": //going back will log the lecturer out

                //confirmation pop up 
                int confirmation = PopUpUtil.displayConfirmInfo("Are you sure you want to logout?");

                //if confirmed, go back to the login window for lecturers + logout current lecturer
                if (confirmation == JOptionPane.YES_OPTION) {
                    NavigationUtil.newFrame(new LoginView());
                    UserController.logOutCurrentUser();
                }
                break;
                
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }
}
