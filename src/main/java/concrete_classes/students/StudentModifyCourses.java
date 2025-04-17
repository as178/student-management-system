/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

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
 * @author Angela Saric (24237573)
 */
public class StudentModifyCourses implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;

    public StudentModifyCourses(Student currenStudent) {
        this.currentStudent = currenStudent;
    }
    
    @Override
    public void showMenu() {
        if (currentStudent.getEnrolledCourses().isEmpty()) {
            System.out.println("> No enrollments yet . . .");
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
                        StudentEnrollCourse enrollCourse = new StudentEnrollCourse(currentStudent);
                        enrollCourse.validateUserInput();
                        validInput = true;
                        break;
                    case "2":
                        StudentWithdrawCourse withdrawCourse = new StudentWithdrawCourse(currentStudent);
                        withdrawCourse.validateUserInput();
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