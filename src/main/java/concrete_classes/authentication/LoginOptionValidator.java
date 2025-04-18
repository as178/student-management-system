/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.authentication;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.run.StudentManagementSystem;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 * 
 * This class validates the initial login option the user chooses.
 * 
 * - It implements the InputValidation interface, overriding its method
 * to check for initial user input and load up all users from respective
 * files into the "public static LinkedHashMap<String, User> currentUsers"
 * accordingly.
 * 
 * - Otherwise it prompts the user for valid input and keeps showing the
 * MainDashboard menu.
 * 
 */
public class LoginOptionValidator implements InputValidationInterface {

    @Override
    public String validateUserInput() {
        
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();

        while (true) {
            NavigationUtil.checkExit(userInput);
            switch (userInput) {
                case "1":
                    FilesManager.readAllStudents();
                    return userInput;
                case "2":
                    FilesManager.readAllLecturers();
                    return userInput;
                case "3":
                    FilesManager.readAllAdmins();
                    return userInput;
                default:
                    HeadersUtil.printHeader("Please pick a valid option.");
                    StudentManagementSystem.mainDashboard.showMenu();
                    userInput = scan.nextLine();
            }
        }
    }
}