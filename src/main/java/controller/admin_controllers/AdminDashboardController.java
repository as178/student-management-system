/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin_controllers;

import concrete_classes.admin.Admin;
import concrete_classes.other.NavigationUtil;
import controller.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.AdminDashboardView;
import view.AdminLoadUserView;
import view.AdminViewDetailsView;
import view.LoginView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 */
public class AdminDashboardController implements ActionListener {

    private AdminDashboardView view;
    private Admin currentAdmin;

    public AdminDashboardController(AdminDashboardView view, Admin currentAdmin) {
        this.view = view;
        this.currentAdmin = currentAdmin;
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
                NavigationUtil.newFrame(new AdminViewDetailsView(currentAdmin));
                break;
            case "2":
                NavigationUtil.newFrame(new AdminLoadUserView(currentAdmin));
        }
    }
}