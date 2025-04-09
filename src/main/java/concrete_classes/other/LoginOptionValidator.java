/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.run.StudentManagementSystem;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public class LoginOptionValidator implements InputValidationInterface {

    @Override
    public String validateUserInput() {

        String userInput;
        Scanner scan = new Scanner(System.in);
        userInput = scan.nextLine();

        while (true) {
            NavigationUtil.checkExit(userInput);
            switch (userInput) {
                case "1":
                    FilesManager.readStudentsFile();
                    return userInput;
                default:
                    HeadersUtil.printHeader("Please pick a valid option.");
                    StudentManagementSystem.mainDashboard.showMenu();
                    userInput = scan.nextLine();
            }
        }
    }
}
