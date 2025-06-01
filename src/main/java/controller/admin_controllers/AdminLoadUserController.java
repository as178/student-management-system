/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin_controllers;

import abstract_classes.User;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import objects.Admin;
import view.admin_view.AdminDashboardView;
import view.admin_view.AdminLoadUserView;
import view.admin_view.AdminModifyPasswordView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Admin user loading and input related
 * implementations.
 *
 */
public class AdminLoadUserController implements ActionListener {

    private AdminLoadUserView view;
    private Admin currentAdmin;
    private String searchUserId;
    private HashMap<String, User> loadedUsers;

    public AdminLoadUserController(AdminLoadUserView view, Admin currentAdmin) {
        this.view = view;
        this.currentAdmin = currentAdmin;
        this.loadedUsers = new HashMap<String, User>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        switch (command) {
            case "1": //search for users within the database to see if searchUserId is valid
                this.searchUserId = view.getUserIdField();
                this.lookUpUser();
                break;
            case "b": //go back to admin dashboard
                NavigationUtil.newFrame(new AdminDashboardView(currentAdmin));
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }

    //helper method for handling logic behind looking up a user 
    private void lookUpUser() {

        if (!searchUserId.isBlank()) {
            //try to look up user by provided id, look through all students first
            StudentDAO studentDAO = new StudentDAO();
            loadedUsers = studentDAO.getAllUsers();

            if (loadedUsers.containsKey(searchUserId)) {
                //load up the dashboard allowing the admin to view user's info + change their password
                NavigationUtil.newFrame(new AdminModifyPasswordView(loadedUsers.get(searchUserId)));
                return;
            }

            //if unsuccessful, look through all lecturers
            LecturerDAO lecturers = new LecturerDAO();
            loadedUsers = lecturers.getAllUsers();

            if (loadedUsers.containsKey(searchUserId)) {
                //load up the dashboard allowing the admin to view user's info + change their password
                NavigationUtil.newFrame(new AdminModifyPasswordView(loadedUsers.get(searchUserId)));
                return;
            }

            //if both attempts are unsuccessful, the user admin is looking for doesn't exist ==> alert admin
            PopUpUtil.displayError("User with ID: " + searchUserId + "\nNot Found!");

        } else { //else, the admin hasn't typed in anything and is alerted
            PopUpUtil.displayError("Please type in a valid ID.");
        }
    }
}
