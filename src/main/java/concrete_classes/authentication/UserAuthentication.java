/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.authentication;

import concrete_classes.admin.Admin;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.lecturer.Lecturer;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.student.Student;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class authenticates the user based on the type of user they are. For
 * example, if the user chose to login as a student their input credentials will
 * be checked against the students records in the "allStudents.txt" file. The
 * user can always go back to the main dashboard or exit the program throughout
 * the entire process.
 *
 */
public class UserAuthentication {

    public boolean login() {

        Scanner scan = new Scanner(System.in);
        String usernameId;
        String password;

        while (true) {
            HeadersUtil.printHeader("Please type in your username (ID)", "or select one of the following:");
            System.out.println("b - Go Back (Login Options)\nx - Exit");
            usernameId = scan.nextLine();
            /*
            if userInput = b, return back to the main dashboard
            if userInput = x, exit the program immediately
             */
            if (NavigationUtil.backOrExit(usernameId)) {
                return false;
            }

            HeadersUtil.printHeader("Please type in your password", "or select one of the following:");
            System.out.println("b - Go Back (Login Options)\nx - Exit");
            password = scan.nextLine();
            if (NavigationUtil.backOrExit(password)) {
                return false;
            }

            /*
            check if user is in the loaded hashmap of all users
            > if they are, load them as the "currentUser"
                - check what type of user they are (e.g. Student), and then:
                    - check their input credentials are correct
                    - if not prompt user again with "Incorrect credentials" message
            > otherwise, prompt user again with "Invalid user" message
             */
            if (FilesManager.currentUsers.containsKey(usernameId)) {

                FilesManager.currentUser = FilesManager.currentUsers.get(usernameId);

                if (FilesManager.currentUser instanceof Student) {
                    Student currentStudent = (Student) FilesManager.currentUser;

                    if (password.equals(currentStudent.getPassword())) {
                        return true;
                    } else {
                        HeadersUtil.printHeader("Incorrect credentials, please try again.");
                    }

                } else if (FilesManager.currentUser instanceof Lecturer) {

                    Lecturer currentLecturer = (Lecturer) FilesManager.currentUser;

                    if (password.equals(currentLecturer.getPassword())) {
                        return true;
                    } else {
                        HeadersUtil.printHeader("Incorrect credentials, please try again.");
                    }

                } else if (FilesManager.currentUser instanceof Admin) {

                    Admin currentAdmin = (Admin) FilesManager.currentUser;

                    if (password.equals(currentAdmin.getPassword())) {
                        return true;
                    } else {
                        HeadersUtil.printHeader("Incorrect credentials, please try again.");
                    }

                }

            } else {
                HeadersUtil.printHeader("Invalid user, please try again.");
            }
        }
    }
}
