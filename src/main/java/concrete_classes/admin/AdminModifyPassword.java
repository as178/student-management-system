/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.admin;

import abstract_classes.User;
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
 * @author williamniven
 */
public class AdminModifyPassword implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private User currentUser;

    public AdminModifyPassword(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Modify User's Password",
                "Please see further options below.");
    }

    //show information about target user
    @Override
    public void showMenu() {
        System.out.println("ID: " + currentUser.getId());
        System.out.println("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
        System.out.println("Date of Birth: " + currentUser.getDateOfBirth());
        System.out.println("Personal Email: " + currentUser.getPersonalEmail());
        System.out.println("University Email: " + currentUser.getUniEmail());
        System.out.println("Phone Number: " + currentUser.getPhoneNumber());
        System.out.println("Gender: " + (currentUser.getGender() == 'F' ? "Female" : "Male"));
        System.out.println("Address: " + currentUser.getAddress());
        HeadersUtil.printHeader("Please pick one of the following.");
        System.out.println("1 - Modify Password\nb - Go Back (Load Another User)\nx - Exit");
    }

    //allows user to set password for target user 
    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

        outerLoop:
        while (true) {

            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine();

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
                    default:
                        HeadersUtil.printHeader("Invalid option.");
                        this.showMenu();
                        userInput = scan.nextLine();
                }
            }
        }
    }

    //takes intput and checks password meets requirements 
    //saves current user with changes back to file 
    private String modifyPassword(Scanner scan) {
        HeadersUtil.printHeader("Please type in the new password below or",
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
        HeadersUtil.printHeader("The user's password has", "been updated successfully!");
        return "continue";
    }
}
