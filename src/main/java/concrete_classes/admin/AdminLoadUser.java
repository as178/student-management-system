/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.admin;

import abstract_classes.User;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class allows admins to search for any user regardless of
 * whether they are a student or a lecturer, and calls on another
 * class to enable them to edit that 'target' user's password.
 *
 */
public class AdminLoadUser implements InputValidationInterface, DashboardInterface, HeaderInterface {

    //asks admin to enter id of target user
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Load User",
                "Please enter the ID of the user",
                "you wish to modify the password of.");
    }

    @Override
    public void showMenu() {
        System.out.println("b - Go Back (Admin Dashboard)\nx - Exit");
    }


    /*
    - admin is prompted to input target id
        - loads all students and checks if target id is present
          in FilesManager currentUsers hashmap 
        - if not found then loads all lecturers the same way and
          checks if target id is present in hashmap
    - AdminModifyPassword class called if target id is found
        - otherwise, admin is re-prompted to enter a valid id
    
    Primary logic behind the validateUserInput method remains
    the same with an outer/inner loop combination for dashboard
    re-displaying and re-prompting in case of invalid input.
    Again the user is always given the option to go back or exit.
     */
    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

        while (true) {

            this.showHeader();
            this.showMenu();

            boolean validInput = false;
            while (!validInput) {
                String userInput = scan.nextLine().trim();

//                if (NavigationUtil.backOrExit(userInput)) {
//                    return "b";
//                }

                FilesManager.readAllStudents();
                if (FilesManager.currentUsers.containsKey(userInput)) {
                    validInput = true;
                    User targetUser = FilesManager.currentUsers.get(userInput);

                    AdminModifyPassword modifyPassword = new AdminModifyPassword(targetUser);
                    modifyPassword.validateUserInput();
                }

                FilesManager.readAllLecturers();
                if (FilesManager.currentUsers.containsKey(userInput)) {
                    validInput = true;
                    User targetUser = FilesManager.currentUsers.get(userInput);

                    AdminModifyPassword modifyPassword = new AdminModifyPassword(targetUser);
                    modifyPassword.validateUserInput();
                }

                if (!validInput) {
                    HeadersUtil.printHeader("User not found,",
                            "please provide a valid ID.");
                    this.showMenu();
                }
            }
        }
    }
}
