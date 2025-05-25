/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import concrete_classes.other.NavigationUtil;
import dao.AdminDAO;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.LoginView;
import view.ProgramLaunchView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any initial welcome screen related logic
 * which happens on the ProgramLaunchView screen.
 *
 */
public class ProgramLaunchController implements ActionListener {

    private ProgramLaunchView view;

    public ProgramLaunchController(ProgramLaunchView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        switch (command) {
            case "1":
                UserController.setCurrentUsers(new StudentDAO().getAllUsers());
                NavigationUtil.newFrame(new LoginView(command));
                break;
            case "2":
                UserController.setCurrentUsers(new LecturerDAO().getAllUsers());
                NavigationUtil.newFrame(new LoginView(command));
                break;
            case "3":
                UserController.setCurrentUsers(new AdminDAO().getAllUsers());
                NavigationUtil.newFrame(new LoginView(command));
                break;
            case "x":
                NavigationUtil.exitProgram();
                break;
        }
    }
}
