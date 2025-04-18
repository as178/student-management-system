/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 *
 * This is the dashboard that shows students their options upon logging in.
 *
 */
public class StudentDashboard implements InputValidationInterface, DashboardInterface, HeaderInterface {

    private Student currentStudent = (Student) FilesManager.currentUser;
    
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Welcome to the Student Dashboard,",
                currentStudent.getFirstName() + " " + currentStudent.getLastName() + "!", "What would you like to do?");
    }

    @Override
    public void showMenu() {
        System.out.println("1 - View My Details\n2 - View My Academic Details\nb - Go Back (Logout)\nx - Exit");
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
                        StudentViewDetails viewDetails = new StudentViewDetails(currentStudent);
                        viewDetails.validateUserInput();
                        validInput = true;
                        break;
                    case "2":
                        StudentViewAcademicDetails viewAcademicDetails = new StudentViewAcademicDetails(currentStudent);
                        viewAcademicDetails.validateUserInput();
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