/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import abstract_classes.User;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any currentUser related implementations,
 * where the user type may not always be known. It also stores the currentUsers
 * HashMap, which will load up all users depending on the required type.
 *
 */
public class UserController {

    private static HashMap<String, User> currentUsers;
    private static User currentUser;

    private UserController() {}
    
    //get + set methods to allow the currentUser(s) to be retrieved
    //or updated if needed
    public static void setCurrentUser(User currentUser) {
        UserController.currentUser = currentUser;
    }

    public static void setCurrentUsers(HashMap<String, User> currentUsers) {
        UserController.currentUsers = currentUsers;
    }

    public static User getCurrentUser() {
        return UserController.currentUser;
    }

    public static HashMap<String, User> getCurrentUsers() {
        return UserController.currentUsers;
    }

    //wipes the currently logged in user from memory
    public static void logOutCurrentUser() {
        UserController.currentUser = null;
    }
}
