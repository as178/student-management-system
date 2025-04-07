/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other_classes;

import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public class ExitOptionsValidation implements InputValidationInterface {

    public static final String goBack = "BACK";
    public static final String exit = "EXIT";
    public String moreOptions;

    public ExitOptionsValidation() {
        this.moreOptions = null;
    }

    public ExitOptionsValidation(String moreOptions) {
        this.moreOptions = moreOptions;
    }

    @Override
    public void validateUserInput(String userInput, Scanner scan) {

        if (moreOptions != null) {
            HeadersUtil.printHeader("b - Go Back", "x - Exit", moreOptions);
        } else {
            HeadersUtil.printHeader("b - Go Back", "x - Exit");
        }
        //userInput = scan
    }
}
