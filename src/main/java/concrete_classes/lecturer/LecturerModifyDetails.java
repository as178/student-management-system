/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

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
public class LecturerModifyDetails implements DashboardInterface, HeaderInterface, InputValidationInterface {
    private Lecturer currentLecturer;

    public LecturerModifyDetails(Lecturer currentLecturer) {
        this.currentLecturer = currentLecturer;
    }

    @Override
    public void showMenu() {
        System.out.println("1 - Password: " + currentLecturer.getSecretPassword());
        System.out.println("2 - Personal Email: " + currentLecturer.getPersonalEmail());
        System.out.println("3 - Phone Number: " + currentLecturer.getPhoneNumber());
        System.out.println("4 - Address: " + currentLecturer.getAddress());
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
                        HeadersUtil.printHeader("Please type in your new password below or",
                                "type 'b' to go back (Modify Menu), or 'x' to exit.");
                        String newPassword = scan.nextLine().trim();

                        if (NavigationUtil.backOrExit(newPassword)) {
                            continue outerLoop;
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
                                continue outerLoop;
                            }
                        }

                        currentLecturer.setPassword(newPassword);
                        FilesManager.saveCurrentLecturer(currentLecturer);
                        HeadersUtil.printHeader("Your password has been updated successfully!");
                        validInput = true;
                        break;

                    case "2":
                        HeadersUtil.printHeader("Please type in your new email below or",
                                "type 'b' to go back (Modify Menu),",
                                " or 'x' to exit.");
                        String newEmail = scan.nextLine().trim();

                        if (NavigationUtil.backOrExit(newEmail)) {
                            continue outerLoop;
                        }
                        while (!ValidationUtil.checkEmail(newEmail)) {
                            HeadersUtil.printHeader("Invalid email address format, please try again.",
                                    "(Type 'b' to go back (Modify Menu),",
                                    " or 'x' to exit.)");
                            newEmail = scan.nextLine().trim();
                            if (NavigationUtil.backOrExit(newEmail)) {
                                continue outerLoop;
                            }
                        }

                        currentLecturer.setPersonalEmail(newEmail);
                        FilesManager.saveCurrentLecturer(currentLecturer);
                        HeadersUtil.printHeader("Your personal email has been updated successfully!");
                        validInput = true;
                        break;

                    case "3":
                        HeadersUtil.printHeader("Please type in your new phone number below",
                                "(e.g. xxx xxx xxxx, +xx xx xxx xxxx, xxxxxxxxxx)",
                                "or type 'b' to go back (Modify Menu),",
                                " or 'x' to exit.");
                        String newPhoneNumber = scan.nextLine().trim();

                        if (NavigationUtil.backOrExit(newPhoneNumber)) {
                            continue outerLoop;
                        }
                        while (!ValidationUtil.checkPhoneNumber(newPhoneNumber)) {
                            HeadersUtil.printHeader("Invalid phone number, please try again.",
                                    "(Type 'b' to go back (Modify Menu),",
                                    " or 'x' to exit.)");
                            newPhoneNumber = scan.nextLine().trim();
                            if (NavigationUtil.backOrExit(newPhoneNumber)) {
                                continue outerLoop;
                            }
                        }

                        currentLecturer.setPhoneNumber(ValidationUtil.formatPhoneNumber(newPhoneNumber));
                        FilesManager.saveCurrentLecturer(currentLecturer);
                        HeadersUtil.printHeader("Your phone number has been updated successfully!");
                        validInput = true;
                        break;

                    case "4":
                        HeadersUtil.printHeader("Please type in your new address below",
                                "(e.g. 123 Street Name Suburb City 1234).", "No commas allowed.", 
                                "Type 'b' to go back (Modify Menu),",
                                " or 'x' to exit.");
                        String newAddress = scan.nextLine();

                        if (NavigationUtil.backOrExit(newAddress)) {
                            continue outerLoop;
                        }
                        while (!ValidationUtil.checkAddress(newAddress)) {
                            HeadersUtil.printHeader("Invalid address, please try again.",
                                    "(Type 'b' to go back (Modify Menu),",
                                    " or 'x' to exit.)");
                            newAddress = scan.nextLine();
                            if (NavigationUtil.backOrExit(newAddress)) {
                                continue outerLoop;
                            }
                        }

                        currentLecturer.setAddress(newAddress);
                        FilesManager.saveCurrentLecturer(currentLecturer);
                        HeadersUtil.printHeader("Your address has been updated successfully!");
                        validInput = true;
                        break;

                    default:
                        HeadersUtil.printHeader("Invalid input, please pick a valid field to modify.");
                        this.showMenu();
                        userInput = scan.nextLine();
                }
            }
        }
    }
}
