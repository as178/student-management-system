/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.ExtraDetailsInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public abstract class UserViewDetails implements DashboardInterface, HeaderInterface, InputValidationInterface, ExtraDetailsInterface {

    protected abstract UserModifyDetails modifyDetails();
    protected User currentUser;
    
    public UserViewDetails(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("My Details");
    }

    @Override
    public void showMenu() {
        System.out.println("ID: " + currentUser.getId());
        this.showExtraDetails();
        System.out.println("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
        System.out.println("Date of Birth: " + currentUser.getDateOfBirth());
        System.out.println("Personal Email: " + currentUser.getPersonalEmail());
        System.out.println("University Email: " + currentUser.getUniEmail());
        System.out.println("Phone Number: " + currentUser.getPhoneNumber());
        System.out.println("Gender: " + (currentUser.getGender() == 'F' ? "Female" : "Male"));
        System.out.println("Address: " + currentUser.getAddress());
        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - Modify Your Details\nb - Go Back (Lecturer Dashboard)\nx - Exit");
    }

    @Override
    public void showExtraDetails(){}
    
    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine().trim();

            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                switch (userInput) {
                    case "1":
                        modifyDetails().validateUserInput();
                        validInput = true;
                        break;
                    default:
                        HeadersUtil.printHeader("Invalid input, please see below", "for valid options.");
                        this.showMenu();
                        userInput = scan.nextLine();
                }
            }
        }
    }
}