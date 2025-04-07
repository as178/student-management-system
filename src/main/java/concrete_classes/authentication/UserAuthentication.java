/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.authentication;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.students.Student;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public class UserAuthentication {

    public boolean login() {

        Scanner scan = new Scanner(System.in);
        String usernameId;
        String password;

        while (true) {
            HeadersUtil.printHeader("Please type in your username (ID)", "or select one of the following:");
            System.out.println("b - Go Back\nx - Exit");
            usernameId = scan.nextLine();
            /*
            if userInput = b, return back to the main dashboard
            if userInput = x, exit the program immediately
             */
            if (NavigationUtil.backOrExit(usernameId)) {
                return false;
            }

            HeadersUtil.printHeader("Please type in your password", "or select one of the following:");
            System.out.println("b - Go Back\nx - Exit");
            password = scan.nextLine();
            if (NavigationUtil.backOrExit(password)) {
                return false;
            }

            if (FilesManager.currentUsers.containsKey(usernameId)) {

                FilesManager.currentUser = FilesManager.currentUsers.get(usernameId);

                if (FilesManager.currentUser instanceof Student) {
                    Student currentStudent = (Student) FilesManager.currentUser;

                    if (password.equals(currentStudent.getPassword())) {
                        return true;
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
