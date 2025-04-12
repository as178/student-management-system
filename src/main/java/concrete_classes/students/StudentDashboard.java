/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

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

    @Override
    public void showHeader() {
        Student currentStudent = (Student) FilesManager.currentUser;
        HeadersUtil.printHeader("Welcome to the Student Dashboard,",
                currentStudent.getFirstName() + " " + currentStudent.getLastName() + "!", "What would you like to do?");
    }

    @Override
    public void showMenu() {
        System.out.println("1 - View My Details\n2 - View My Currently Enrolled Courses"
                + "\n3 - View My Grades\nb - Go Back\nx - Exit");
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
                        StudentViewDetails.viewMyDetails();
                        validInput = true;
                        break;
                    case "2":
                        StudentViewDetails.currentlyEnrolledCourses();
                        validInput = true;
                        break;
                    case "3":
                        StudentViewDetails.veiwEditDetails();
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
