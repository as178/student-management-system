/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin_controllers;

import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import controller.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import objects.Admin;
import view.LoginView;
import view.admin_view.AdminLoadUserView;
import view.admin_view.AdminViewDetailsView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Admin dashboard related
 * implementations.
 *
 */
public class AdminDashboardController implements ActionListener {

    private Admin currentAdmin;

    public AdminDashboardController(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "1": //dashboard for viewing and modifying personal information
                NavigationUtil.newFrame(new AdminViewDetailsView(currentAdmin));
                break;
            case "2": //dashboard for loading up any student/lecturer user to change their password
                NavigationUtil.newFrame(new AdminLoadUserView(currentAdmin));
                break;
            case "b": //going back will log the admin out

                //confirmation pop up 
                int confirmation = PopUpUtil.displayConfirmInfo("Are you sure you want to logout?");

                //if confirmed, go back to the login window for admins + logout current admin
                if (confirmation == JOptionPane.YES_OPTION) {
                    NavigationUtil.newFrame(new LoginView("3"));
                    UserController.logOutCurrentUser();
                }
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }
}
