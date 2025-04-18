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
 * @author williamniven
 */
public class AdminDashboard implements InputValidationInterface, DashboardInterface, HeaderInterface {

    private Admin currentAdmin = (Admin) FilesManager.currentUser;

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Welcome to the Admin Dashboard,",
                currentAdmin.getFirstName() + " " + currentAdmin.getLastName() + "!", "What would you like to do?");
    }

    @Override
    public void showMenu() {
        System.out.println("1 - View My Details\n2 - Change a User's Password\nb - Go Back (Logout)\nx - Exit");
    }

    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

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
                        AdminViewDetails viewDetails = new AdminViewDetails(currentAdmin);
                        viewDetails.validateUserInput();
                        validInput = true;
                        break;
                    case "2":
                        AdminLoadUser loadUser = new AdminLoadUser();
                        loadUser.validateUserInput();
                        validInput = true;
                        break;
                    default:
                        HeadersUtil.printHeader("Please pick a valid option.");
                        this.showMenu();
                        userInput = scan.nextLine();
                }
            }
        }
    }
}
