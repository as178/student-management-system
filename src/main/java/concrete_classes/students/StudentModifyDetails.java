/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

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
public class StudentModifyDetails implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;

    public StudentModifyDetails(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    @Override
    public void showMenu() {
        System.out.println("1 - Password: " + currentStudent.getSecretPassword());
        System.out.println("2 - Personal Email: " + currentStudent.getPersonalEmail());
        System.out.println("3 - Phone Number: " + currentStudent.getPhoneNumber());
        System.out.println("4 - Address: " + currentStudent.getAddress());
        System.out.println("b - Go Back\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("What would you like to modify today?");
    }

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();

            String userInput = scan.nextLine().trim().toLowerCase();

            if (NavigationUtil.backOrExit(userInput)) {
                return "b";
            }

            boolean validInput = false;
            while (!validInput) {
                switch (userInput) {
                    case "1":
                        HeadersUtil.printHeader("Please type in your new email address below.");
                        String newEmail = scan.nextLine();
                        //currentStudent.setPersonalEmail(newEmail);
                        HeadersUtil.printHeader("Personal email updated!");
                        break;
                    case "2":
                        System.out.print("Enter new phone number: ");
                        String newPhone = scan.nextLine();
                        //currentStudent.setPhoneNumber(newPhone);
                        HeadersUtil.printHeader("Phone number updated!");
                        break;
                    case "3":
                        System.out.print("Enter new address: ");
                        String newAddress = scan.nextLine();
                        //currentStudent.setAddress(newAddress);
                        HeadersUtil.printHeader("Address updated!");
                        break;
                    default:
                        HeadersUtil.printHeader("Invalid input.");
                }
            }
        }
    }
}
