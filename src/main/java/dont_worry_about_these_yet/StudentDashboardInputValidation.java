/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dont_worry_about_these_yet;

import interfaces.InputValidationInterface;

/**
 *
 * @author williamniven
 */
public class StudentDashboardInputValidation implements InputValidationInterface {

    @Override
    public String validateUserInput() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    @Override
//    public String validateUserInput() {
//        
//        String userInput;
//        boolean validInput = false;
//
//        System.out.println("Please pick an option: ");
//
//        while (!validInput) {
//            if (userInput.equalsIgnoreCase("x")) {
//                validInput = true;
//                ProgramShutdown.shutdown();
//            } else if (userInput.equalsIgnoreCase("1")) {
//                validInput = true;
//                StudentManager.viewMyDetails();
//                
//                
//            } else if (userInput.equalsIgnoreCase("2")) {
//                validInput = true;
//                StudentManager.currentlyEnrolledCourses();
//            } else if (userInput.equalsIgnoreCase("3")) {
//                validInput = true;
//                
//            } else {
//                HeadersUtil.printHeader("Please pick a valid option.");
//                StudentDashboard S = new StudentDashboard();
//                S.showHeader();
//                S.showHeader();
//                userInput = scan.nextLine();
//            }
//        }
//    }
}
