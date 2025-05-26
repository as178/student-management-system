/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import objects.Lecturer;
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
 * This is the dashboard that shows lecturers their options upon logging in.
 *
 */
public class LecturerDashboard implements InputValidationInterface, DashboardInterface, HeaderInterface {

    private Lecturer currentLecturer = (Lecturer) FilesManager.currentUser;

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Welcome to the Lecturer Dashboard,",
                currentLecturer.getFirstName() + " " + currentLecturer.getLastName() + "!", "What would you like to do?");
    }

    //prints options to lecturer
    //1 - View My Details
    //2 - Manage My Courses
    @Override
    public void showMenu() {
        System.out.println("1 - View My Details\n2 - Manage My Courses\nb - Go Back (Logout)\nx - Exit");
    }


    /*
    The following method:
    - creates isntances of classes by passing in currentLectuerer object
    - enables lecturers to enter dashboards for each option
    
    The primary logic behind the validateUserInput method remains
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
//                        LecturerViewDetails viewDetails = new LecturerViewDetails(currentLecturer);
//                        //displays the lecturer's personal details
//                        viewDetails.validateUserInput();
                        validInput = true;
                        break;
                    case "2":
                        LecturerManageCourses manageCourses = new LecturerManageCourses(currentLecturer);
                        //displays courses the lecturer is teaching & further options 
                        manageCourses.validateUserInput();
                        validInput = true; //confirms valid input
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
