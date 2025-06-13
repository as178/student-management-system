/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import abstract_classes.User;
import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.LoginView;
import view.ProgramLaunchView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any initial login related logic which
 * happens on the LoginView screen.
 *
 */
public class LoginController implements ActionListener {

    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        //if the user wishes to shutdown the program
        if ("x".equalsIgnoreCase(command)) {
            NavigationUtil.exitProgram();
            return;
        }

        //if the user wishes to go back to the launch view
        if ("b".equalsIgnoreCase(command)) {
            NavigationUtil.newFrame(new ProgramLaunchView());
            return;
        }

        //else get the username and password from the view's text fields
        String username = view.getUsername();
        String password = view.getPassword();

        //if the currentUsers hashmap contains the id of the user trying to log in
        if (UserController.getCurrentUsers().containsKey(username)) {

            //retrieve the user object from the hashmap
            User user = UserController.getCurrentUsers().get(username);

            //check if the input password matches the password from the retrieved user in the database
            if (user.getPassword().equals(password)) {
                
                //if password is correct set the currentUser as this user
                UserController.setCurrentUser(user);

                //display the appropriate user dashboard based on their type
                user.userMainDashboard();
                
            } else { //if the user exists in the database but the password is wrong, alert the user
                PopUpUtil.displayError("Incorrect credentials, please try again.");
            }
        } else { //if there is no such user in the database, alert the user
            PopUpUtil.displayError("Invalid user, please try again.");
        }
    }
}
