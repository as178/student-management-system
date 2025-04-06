/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.authentication;

import concrete_classes.components.Student;
import concrete_classes.file_input_output.FilesManager;
import java.util.Scanner;
import other_classes.HeadersUtil;

/**
 *
 * @author Angela Saric (24237573)
 */
public class UserAuthentication {

    public void login(Scanner scan) {
    
        boolean validInput = false;
        String usernameId;
        String password;

        while (!validInput) {
            System.out.println("Please type in your username (ID):");
            usernameId = scan.nextLine();

            System.out.println("Please type in your password:");
            password = scan.nextLine();

            if (FilesManager.currentUsers.containsKey(usernameId)) {

                FilesManager.currentUser = FilesManager.currentUsers.get(usernameId);

                if (FilesManager.currentUser instanceof Student) {
                    Student currentStudent = (Student) FilesManager.currentUser;
                    
                    if (password.equals(currentStudent.getPassword())) {
                        validInput = true;
                    } else {
                        HeadersUtil.printHeader("Incorrect credentials,", "please try again.");
                    }
                }

            } else {
                HeadersUtil.printHeader("Invalid user,", "please try again.");
            }
        }
    }
}