/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other_classes;

import concrete_classes.StudentManager;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author williamniven
 */
public class EditUserInputValidation implements InputValidationInterface {

    @Override
    public void validateUserInput(String userInput, Scanner scan) {

        boolean validInput = false;
        userInput = scan.nextLine();

        while (!validInput) {
            if (userInput.equalsIgnoreCase("x")) {
                ProgramShutdown.shutdown();
            } else if (userInput.equalsIgnoreCase("1")) {
                validInput = true;
                //edit Email
            } else if (userInput.equalsIgnoreCase("2")) {
                validInput = true;
                //Edit phone number
            } else if (userInput.equalsIgnoreCase("3")) {
                validInput = true;
                //Edit address
            } else if (userInput.equalsIgnoreCase("4")) {
                validInput = true;
                //back
            } else {
                HeadersUtil.printHeader("Please pick a valid option.");
                StudentManager.viewMyDetails();
            }
        }
    }
}
