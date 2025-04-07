/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other_classes;

import concrete_classes.authentication.UserAuthentication;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.run.StudentManagementSystemRun;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public class LoginUserInputValidation implements InputValidationInterface {

    @Override
    public void validateUserInput(String userInput, Scanner scan) {

        boolean validInput = false;
        UserAuthentication userAuthentication = new UserAuthentication();

        System.out.println("Please pick an option (login as a): ");
        userInput = scan.nextLine();

        while (!validInput) {
            if (userInput.equalsIgnoreCase("x")) {
                ProgramShutdown.shutdown();
            } else if (userInput.equalsIgnoreCase("1")) {
                validInput = true;
                FilesManager.readStudentsFile();
                userAuthentication.login(scan);               
            } else {
                HeadersUtil.printHeader("Please pick a valid option.");
                StudentManagementSystemRun.mainDashboard.showMenu();
                userInput = scan.nextLine();
            }
        }
    }
}
