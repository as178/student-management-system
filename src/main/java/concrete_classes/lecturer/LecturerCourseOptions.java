/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.courses.Course;
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
public class LecturerCourseOptions implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;

    public LecturerCourseOptions(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    @Override
    public void showMenu() {
        System.out.println("1 - List Students");
        System.out.println("2 - Edit Course");
        System.out.println("b - Go Back (Manage Courses)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Course Options",
                "Below are available options for:",
                currentCourse.getCourseId() + ", " + currentCourse.getCourseName());
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
                        LecturerCourseListStudents courseListStudents = new LecturerCourseListStudents(currentCourse);
                        courseListStudents.validateUserInput();
                        FilesManager.readAllLecturers();
                        validInput = true;
                        break;
                    case "2":
                        LecturerEditCourse editCourse = new LecturerEditCourse(currentCourse);
                        editCourse.validateUserInput();
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
