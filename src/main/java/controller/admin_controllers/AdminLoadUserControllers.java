/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin_controllers;

import abstract_classes.User;
import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
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
 */
public class AdminLoadUserControllers implements ActionListener {

    private AdminLoadUserView view;
    private Admin currentAdmin;
    private String searchUserID;
    
    private  HashMap<String, User> loadedUsers = new  HashMap<String, User>(); 

    public AdminLoadUserControllers(AdminLoadUserView view, Admin currentAdmin) {
        this.view = view;
        this.currentAdmin = currentAdmin;
        this.searchUserID = searchUserID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("x".equalsIgnoreCase(command)) {
            NavigationUtil.exitProgram();
            return;
        }

        if ("b".equalsIgnoreCase(command)) {
            NavigationUtil.newFrame(new AdminDashboardView(currentAdmin));
        }

        if ("1".equalsIgnoreCase(command)) {
            boolean found = false;
            searchUserID = view.getUserIdField().getText().trim();
            
            //try to load user by the provided id 
            StudentDAO students = new StudentDAO();
            loadedUsers = students.getAllUsers();
            if (loadedUsers.containsKey(searchUserID)){
                found = true;
                //NavigationUtil.newFrame(new AdminModifyPasswordView((User) loadedUsers.get(searchUserID), currentAdmin));
            }

            LecturerDAO lecturers = new LecturerDAO();
            loadedUsers = lecturers.getAllUsers();
            if (loadedUsers.containsKey(searchUserID)){
                found = true;
                //NavigationUtil.newFrame(new AdminModifyPasswordView((User) loadedUsers.get(searchUserID), currentAdmin));
            }
            
            if (found == false){
                PopUpUtil.displayError("User with ID: " + searchUserID + " not found!");
            }
        }
    }
}