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
public class VeiwUserInputValidation implements InputValidationInterface {

    @Override
    public String validateUserInput() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    @Override
//    public void validateUserInput(String userInput, Scanner scan) {
//
//        boolean validInput = false;
//        userInput = scan.nextLine();
//
//        while (!validInput) {
//            if (userInput.equalsIgnoreCase("x")) {
//                ProgramShutdown.shutdown();
//            } else if (userInput.equalsIgnoreCase("1")) {
//                validInput = true;
//                StudentManager.veiwEditDetails();
//                
//            } else if (userInput.equalsIgnoreCase("2")) {
//                validInput = true;
//                //back method
//            } else {
//                HeadersUtil.printHeader("Please pick a valid option.");
//                StudentManager.viewMyDetails();
//            }
//        }
//    }
}
