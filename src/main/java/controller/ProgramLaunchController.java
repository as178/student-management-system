/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import utility_classes.NavigationUtil;
import dao.AdminDAO;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.LoginView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any initial welcome screen related logic
 * which happens on the ProgramLaunchView screen.
 *
 */
public class ProgramLaunchController implements ActionListener {

    public ProgramLaunchController() {}

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        switch (command) {
            case "1":
                //set up login screen for student to login, load up respective currentUsers hashmap used for checking credentials
                UserController.setCurrentUsers(new StudentDAO().getAllUsers());
                NavigationUtil.newFrame(new LoginView(command));
                break;
            case "2":
                //set up login screen for lecturer to login, load up respective currentUsers hashmap used for checking credentials                
                UserController.setCurrentUsers(new LecturerDAO().getAllUsers());
                NavigationUtil.newFrame(new LoginView(command));
                break;
            case "3":
                //set up login screen for admin to login, load up respective currentUsers hashmap used for checking credentials                
                UserController.setCurrentUsers(new AdminDAO().getAllUsers());
                NavigationUtil.newFrame(new LoginView(command));
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }
}
