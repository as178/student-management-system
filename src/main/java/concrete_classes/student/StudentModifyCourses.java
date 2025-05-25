/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

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
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays the student's current enrollments and provides them with
 * further options (to enroll or withdraw from courses).
 *
 */
public class StudentModifyCourses implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;

    public StudentModifyCourses(Student currenStudent) {
        this.currentStudent = currenStudent;
    }

    @Override
    public void showMenu() {
        //if the student isn't enrolled in any courses at the moment
        if (currentStudent.getEnrolledCourses().isEmpty()) {
            System.out.println("> No enrollments yet . . .");

            //else, display their current courses
        } else {
            System.out.println("[ Your Current Courses ]");
            for (String courseId : currentStudent.getEnrolledCourses().keySet()) {
                for (Course currentCourse : FilesManager.allCourses) {
                    if (currentCourse.getCourseId().equals(courseId)) {
                        System.out.println("> " + currentCourse.getCourseId()
                                + ", " + currentCourse.getCourseName());
                    }
                }
            }
        }
        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - Add a Course\n2 - Withdraw From a Course\nb - Go Back (My Academic Details)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Change Your Courses", "Please see below for available options.");
    }

    /*
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
                        StudentEnrollCourse enrollCourse = new StudentEnrollCourse(currentStudent);
                        enrollCourse.validateUserInput(); //enable the student to enroll into more courses
                        validInput = true;
                        break;
                    case "2":
                        StudentWithdrawCourse withdrawCourse = new StudentWithdrawCourse(currentStudent);
                        withdrawCourse.validateUserInput(); //enable the student to withdraw from current enrollments
                        validInput = true; //confirm valid input
                        break;
                    default: //otherwise, re-prompt
                        HeadersUtil.printHeader("Invalid input, please see below", "for valid options.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
                }
            }
        }
    }
}
