/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.authentication;

import concrete_classes.components.Student;
import concrete_classes.file_input_output.FilesManager;
import java.util.Scanner;
import other_classes.HeadersUtil;
import other_classes.ProgramShutdown;

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
            System.out.println("b - Go Back\nx - Exit\nPlease type in your username (ID):");
            usernameId = scan.nextLine();

            if (usernameId.equalsIgnoreCase("b")){
                return;
            } else if (usernameId.equalsIgnoreCase("x")){
                ProgramShutdown.shutdown();
            }
            
            System.out.println("b - Go Back\nx - Exit\nPlease type in your password:");
            password = scan.nextLine();

//            if (usernameId.equalsIgnoreCase("b")){
//                
//            } else if (usernameId.equalsIgnoreCase("x")){
//                
//            }
            
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