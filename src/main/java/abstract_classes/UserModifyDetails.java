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
 * @author Angela Saric (24237573)
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

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        outerLoop:
        while (true) {
            this.showHeader();
            this.showMenu();

            String userInput = scan.nextLine().trim().toLowerCase();

            //inner loop
            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                switch (userInput) {
                    case "1":
                        if (this.modifyPassword(scan).equalsIgnoreCase("b")) {
                            continue outerLoop;
                        }
                        validInput = true;
                        break;

                    case "2":
                        if (this.modifyEmail(scan).equalsIgnoreCase("b")) {
                            continue outerLoop;
                        }
                        validInput = true;
                        break;

                    case "3":
                        if (this.modifyPhoneNumber(scan).equalsIgnoreCase("b")) {
                            continue outerLoop;
                        }
                        validInput = true;
                        break;

                    case "4":
                        if (this.modifyAddress(scan).equalsIgnoreCase("b")) {
                            continue outerLoop;
                        }
                        validInput = true;
                        break;

                    default:
                        HeadersUtil.printHeader("Invalid input, please pick", "a valid field to modify.");
                        this.showMenu();
                        userInput = scan.nextLine();
                }
            }
        }
    }

    private String modifyPassword(Scanner scan) {
        HeadersUtil.printHeader("Please type in your new password below or",
                "type 'b' to go back (Modify Menu), or 'x' to exit.");
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
        HeadersUtil.printHeader("Your password has been updated successfully!");
        return "continue";
    }

    private String modifyEmail(Scanner scan) {
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

    private String modifyPhoneNumber(Scanner scan) {
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
        HeadersUtil.printHeader("Your phone number has been updated successfully!");
        return "continue";
    }

    private String modifyAddress(Scanner scan) {
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