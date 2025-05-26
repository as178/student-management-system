/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import objects.Course;
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
 * This class displays options the lecturer has for a specific
 * course they are teaching. They may choose to edit course details
 * or go through to see a list of currently enrolled students.
 * 
 */
public class LecturerCourseOptions implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;

    public LecturerCourseOptions(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    //display options for the course to the user
    @Override
    public void showMenu() {
        System.out.println("1 - List Students");
        System.out.println("2 - Edit Course");
        System.out.println("b - Go Back (Manage Courses)\nx - Exit");
    }

    //display the course id and name to the user 
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Course Options",
                "Below are available options for:",
                currentCourse.getCourseId() + ", " + currentCourse.getCourseName());
    }

    /*
    The following method:
    - displays header and menu options 
    - has a switch case for the two options for the current course
        - each case creates an instance of the respective class, where
          we are passing in the currentCourse object
    
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
                        LecturerCourseListStudents courseListStudents = new LecturerCourseListStudents(currentCourse);
                        courseListStudents.validateUserInput(); //see students
                        FilesManager.readAllLecturers();
                        validInput = true;
                        break;
                    case "2":
                        LecturerEditCourse editCourse = new LecturerEditCourse(currentCourse);
                        editCourse.validateUserInput(); // edit the course's details
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