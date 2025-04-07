/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other_classes;

import concrete_classes.StudentDashboard;
import concrete_classes.StudentManager;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author williamniven
 */
public class MainStudentDashboardInputValidation implements InputValidationInterface {

    @Override
    public void validateUserInput(String userInput, Scanner scan) {
        boolean validInput = false;

        System.out.println("Please pick an option: ");

        while (!validInput) {
            if (userInput.equalsIgnoreCase("x")) {
                validInput = true;
                ProgramShutdown.shutdown();
            } else if (userInput.equalsIgnoreCase("1")) {
                validInput = true;
                StudentManager.viewMyDetails();
                
                
            } else if (userInput.equalsIgnoreCase("2")) {
                validInput = true;
                StudentManager.currentlyEnrolledCourses();
            } else if (userInput.equalsIgnoreCase("3")) {
                validInput = true;
                
            } else {
                HeadersUtil.printHeader("Please pick a valid option.");
                StudentDashboard S = new StudentDashboard();
                S.showHeader();
                S.showHeader();
                userInput = scan.nextLine();
            }
        }
    }
}
