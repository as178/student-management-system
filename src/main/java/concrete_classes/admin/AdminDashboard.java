/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.admin;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This is the dashboard that shows admins their options upon logging in.
 * 
 */
public class AdminDashboard implements InputValidationInterface, DashboardInterface, HeaderInterface {

    private Admin currentAdmin = (Admin) FilesManager.currentUser;

    //print current admins full name 
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Welcome to the Admin Dashboard,",
                currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!", "What would you like to do?");
    }

    //print admins options
    @Override
    public void showMenu() {
        System.out.println("1 - View My Details\n2 - Change a User's Password\nb - Go Back (Logout)\nx - Exit");
    }

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
//                if (NavigationUtil.backOrExit(userInput)) {
//                    return "b";
//                }
                switch (userInput) {
                    case "1": 
                        AdminViewDetails viewDetails = new AdminViewDetails(currentAdmin);
                        //triggers the personal details viewing dashboard
                        viewDetails.validateUserInput(); 
                        validInput = true; //confirms valid input, breaks the inner loop
                        break;
                    case "2":
                        AdminLoadUser loadUser = new AdminLoadUser();
                        //other option when admin wants to edit a user's password
                        loadUser.validateUserInput();
                        validInput = true;
                        break;
                    default: //otherwise, re-prompt
                        HeadersUtil.printHeader("Please pick a valid option.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
                }
            }
        }
    }
}
