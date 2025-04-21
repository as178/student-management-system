/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.ValidationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The main modify class for editing personal details, which is used by every
 * user. The currentUser is passed into the class and their get & set methods
 * are used for information access. The class implements needed dashboards
 * and overrides their methods as required.
 *
 */
public abstract class UserModifyDetails implements DashboardInterface, HeaderInterface, InputValidationInterface {

    protected User currentUser;

    public UserModifyDetails(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void showMenu() {
        System.out.println("1 - Password: " + currentUser.getSecretPassword());
        System.out.println("2 - Personal Email: " + currentUser.getPersonalEmail());
        System.out.println("3 - Phone Number: " + currentUser.getPhoneNumber());
        System.out.println("4 - Address: " + currentUser.getAddress());
        System.out.println("b - Go Back (My Details)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("What would you like to modify?");
    }

    /*
    Validates the user's input in relation to what they
    want to modify. If no valid input is chosen, the user is
    prompted again. As with the rest of our program, the user
    always has the choice to go back to the previous dashboard
    or completely exit the program.
     */
    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        outerLoop:
        while (true) {
            this.showHeader();
            this.showMenu();

            String userInput = scan.nextLine().trim();

            //inner loop
            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                switch (userInput) {
                    case "1": //modify user's password
                        if (this.modifyPassword(scan).equalsIgnoreCase("b")) {
                            continue outerLoop; //restart the loop if user wishes to go back
                        }
                        validInput = true; //otherwise confirm valid input
                        break;

                    case "2": //modify user's email
                        if (this.modifyEmail(scan).equalsIgnoreCase("b")) {
                            continue outerLoop;
                        }
                        validInput = true;
                        break;

                    case "3": //modify user's phone number
                        if (this.modifyPhoneNumber(scan).equalsIgnoreCase("b")) {
                            continue outerLoop;
                        }
                        validInput = true;
                        break;

                    case "4": //modify user's address
                        if (this.modifyAddress(scan).equalsIgnoreCase("b")) {
                            continue outerLoop;
                        }
                        validInput = true;
                        break;

                    default: //invalid input ==> re-prompt
                        HeadersUtil.printHeader("Invalid input, please pick", "a valid field to modify.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
                }
            }
        }
    }

    /*
    The following are protected methods that the validateUserInput
    method above calls on if the user wishes to change their details.
    They are protected as they may be used individually within
    classes that extend this class, or if we wish to tweak their
    behaviour by overriding them for any given user.
    
    They:
    - prompt the user for their new information
    - always check if the user wants to go back or exit
    - if not they call on ValidationUtil's methods which
      enforce our restrictions for each of the fields
      (and help with further formatting)
        - if the user's input doesn't match these
          restrictions, they are re-prompted with
          descriptive messages to remind them of the
          restrictions
    - finally, the new valid information is put in
      place via the user's set methods
    - the user is then immediately saved using the
      FilesManager method for saving the currentUser
      and a success message is printed
    - return "continue" as a sign of valid input
     */
    protected String modifyPassword(Scanner scan) {
        HeadersUtil.printHeader("Please type in the new password below or",
                "type 'b' to go back (Modify Menu),",
                "or 'x' to exit.");
        String newPassword = scan.nextLine().trim();

        if (NavigationUtil.backOrExit(newPassword)) {
            return "b";
        }
        while (!ValidationUtil.checkPassword(newPassword)) {
            HeadersUtil.printHeader("Passwords cannot contain commas or",
                    "periods, be empty or blank, and",
                    "must be at least 8 characters long.",
                    "Please try again.",
                    "(Type 'b' to go back (Modify Menu),",
                    " or 'x' to exit.)");
            newPassword = scan.nextLine().trim();
            if (NavigationUtil.backOrExit(newPassword)) {
                return "b";
            }
        }

        currentUser.setPassword(newPassword);
        FilesManager.saveCurrentUser(currentUser);
        HeadersUtil.printHeader("Password updated successfully!");
        return "continue";
    }

    protected String modifyEmail(Scanner scan) {
        HeadersUtil.printHeader("Please type in your new email below or",
                "type 'b' to go back (Modify Menu),",
                " or 'x' to exit.");
        String newEmail = scan.nextLine().trim();

        if (NavigationUtil.backOrExit(newEmail)) {
            return "b";
        }
        while (!ValidationUtil.checkEmail(newEmail)) {
            HeadersUtil.printHeader("Invalid email address format, please try again.",
                    "(Type 'b' to go back (Modify Menu),",
                    " or 'x' to exit.)");
            newEmail = scan.nextLine().trim();
            if (NavigationUtil.backOrExit(newEmail)) {
                return "b";
            }
        }

        currentUser.setPersonalEmail(newEmail);
        FilesManager.saveCurrentUser(currentUser);
        HeadersUtil.printHeader("Your personal email has been updated successfully!");
        return "continue";
    }

    protected String modifyPhoneNumber(Scanner scan) {
        HeadersUtil.printHeader("Please type in your new phone number below",
                "(e.g. xxx xxx xxxx, +xx xx xxx xxxx, xxxxxxxxxx)",
                "or type 'b' to go back (Modify Menu),",
                " or 'x' to exit.");
        String newPhoneNumber = scan.nextLine().trim();

        if (NavigationUtil.backOrExit(newPhoneNumber)) {
            return "b";
        }
        while (!ValidationUtil.checkPhoneNumber(newPhoneNumber)) {
            HeadersUtil.printHeader("Invalid phone number, please try again.",
                    "(Type 'b' to go back (Modify Menu),",
                    " or 'x' to exit.)");
            newPhoneNumber = scan.nextLine().trim();
            if (NavigationUtil.backOrExit(newPhoneNumber)) {
                return "b";
            }
        }

        currentUser.setPhoneNumber(ValidationUtil.formatPhoneNumber(newPhoneNumber));
        FilesManager.saveCurrentUser(currentUser);
        HeadersUtil.printHeader("Your phone number has been", "updated successfully!");
        return "continue";
    }

    protected String modifyAddress(Scanner scan) {
        HeadersUtil.printHeader("Please type in your new address below",
                "(e.g. 123 Street Name Suburb City 1234).", "No commas allowed.",
                "Type 'b' to go back (Modify Menu),",
                " or 'x' to exit.");
        String newAddress = scan.nextLine();

        if (NavigationUtil.backOrExit(newAddress)) {
            return "b";
        }
        while (!ValidationUtil.checkAddress(newAddress)) {
            HeadersUtil.printHeader("Invalid address, please try again.",
                    "(Type 'b' to go back (Modify Menu),",
                    " or 'x' to exit.)");
            newAddress = scan.nextLine();
            if (NavigationUtil.backOrExit(newAddress)) {
                return "b";
            }
        }

        currentUser.setAddress(newAddress);
        FilesManager.saveCurrentUser(currentUser);
        HeadersUtil.printHeader("Your address has been updated successfully!");
        return "continue";
    }
}