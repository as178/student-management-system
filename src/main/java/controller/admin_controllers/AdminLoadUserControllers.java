/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin_controllers;

import abstract_classes.User;
import concrete_classes.admin.Admin;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import view.AdminDashboardView;
import view.AdminLoadUserView;

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
            
            searchUserID = view.getUserIdField().getText().trim();
            
            //try to load user by the provided id 
            StudentDAO students = new StudentDAO();
            loadedUsers = students.getAllUsers();
            if (loadedUsers.containsKey(searchUserID)){

            }

            LecturerDAO lecturers = new LecturerDAO();
            loadedUsers = lecturers.getAllUsers();
            if (loadedUsers.containsKey(searchUserID)){

            }else{
                PopUpUtil.displayError("User with ID: " + searchUserID + " not found!");
            }
        }
    }
}
