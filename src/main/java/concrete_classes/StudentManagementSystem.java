/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes;

import java.util.Scanner;
import other_classes.HeadersUtil;
import other_classes.LoginUserInputValidation;

/**
 *
 * @author Angela Saric (24237573)
 * 
 * This starts up the program; main method.
 */
public class StudentManagementSystem {
    
    public static MainDashboard mainDashboard = new MainDashboard();
    
    public static void main(String[] args) {
        HeadersUtil.printHeader("Welcome to the", "Student Management System!", "(Login Required)");
        mainDashboard.showMenu();
        
        Scanner scan = new Scanner(System.in);
        String userInput = new String();
        
        LoginUserInputValidation loginUserValidation = new LoginUserInputValidation();
        loginUserValidation.validateUserInput(userInput, scan);
        
        /*
        to implement now:
        - the "go back" option
        - the logout option
        these options should always be available (?), as well as the shutdown option
        
        next to implement:
        - writing method for students
        - making an interface for authentication, login()
        */
    }
}