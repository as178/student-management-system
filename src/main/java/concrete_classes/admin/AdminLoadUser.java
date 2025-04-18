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
 * @author williamniven
 */
public class AdminLoadUser implements InputValidationInterface, DashboardInterface, HeaderInterface {

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Load User,",
                "Enter the users ID:");
    }

    @Override
    public void showMenu() {
        System.out.println("b - Go Back (Admin Dashboard)\nx - Exit");
    }

    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in); 

        while (true) {

            this.showHeader();
            this.showMenu();

            boolean validInput = false;
            while (!validInput) {
                String userInput = scan.nextLine();
                
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

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

                if (validInput == false) {
                    HeadersUtil.printHeader("User not found,",
                            "Enter a valid ID");
                }
            }
        }
    }
}
