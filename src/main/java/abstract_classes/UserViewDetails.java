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
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The main viewing class for viewing personal details, which is used by every
 * user. The currentUser is passed into the class and their get & set methods
 * are used for information access. The class implements needed dashboards and
 * overrides their methods as required.
 *
 */
public abstract class UserViewDetails implements DashboardInterface, HeaderInterface, InputValidationInterface, ExtraDetailsInterface {

    /*
    Below is an abstract method which will be overriden by
    every class that extends this class. The method will return
    a user specific modify class which will extend UserModifyDetails.
     */
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

        /*
        calling the extra details method in
        case user has more information to display
         */
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

    /*
    The following method is overriden from the ExtraDetailsInterface
    interface. It is empty by default but can be overriden by user viewing
    classes if users have additional variables/information to be displayed.
     */
    @Override
    public void showExtraDetails() {}

    /*
    Primary logic behind the validateUserInput method remains
    the same with an outer/inner loop combination for dashboard
    re-displaying and re-prompting in case of invalid input.
    Again the user is always given the option to go back or exit.
     */
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
                        /*
                        the only valid option which will trigger the
                        modify menu from the user specific modify class,
                        which will be returned when calling modifyDetails()
                         */
                        modifyDetails().validateUserInput();
                        validInput = true; //confirm valid input
                        break;
                    default: //otherwise, re-prompt the user
                        HeadersUtil.printHeader("Invalid input, please see below", "for valid options.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
                }
            }
        }
    }
}
