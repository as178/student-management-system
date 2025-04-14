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
public class StudentViewDetails implements DashboardInterface, HeaderInterface, InputValidationInterface {
    
    private Student currentStudent;
    
    public StudentViewDetails(Student currentStudent){
        this.currentStudent = currentStudent;
    }
    
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("My Details");
    }

    @Override
    public void showMenu() {
        System.out.println("ID: " + currentStudent.getId());
        System.out.println("Name: " + currentStudent.getFirstName() + " " + currentStudent.getLastName());
        System.out.println("Date of Birth: " + currentStudent.getDateOfBirth());
        System.out.println("Personal Email: " + currentStudent.getPersonalEmail());
        System.out.println("University Email: " + currentStudent.getUniEmail());
        System.out.println("Phone Number: " + currentStudent.getPhoneNumber());
        System.out.println("Gender: " + currentStudent.getGender());
        System.out.println("Address: " + currentStudent.getAddress());
        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - Modify Your Details\nb - Go Back\nx - Exit");
    }

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
                        StudentModifyDetails modifyDetails = new StudentModifyDetails(currentStudent);
                        modifyDetails.validateUserInput();
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