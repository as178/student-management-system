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
 * Work in progress . . .
 * @author Angela Saric (24237573)
 */
public class StudentModifyCourses implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;

    public StudentModifyCourses(Student currenStudent) {
        this.currentStudent = currenStudent;
    }

    /*
    - the student can see a dynamic, short list of their current courses (courseId only)
    - then they can pick to either add more courses or remove courses
    
    ADDING:
    - available only if the student is taking less than 8 courses
    - student is shown a dynamic list of available courses they can take
        - can't be courses they are currently taking
        - also can't be courses they have previously taken (NOT withdrawn from or failed, so e.g.
          if COMP610 has a failed grade/was withdrawn from, the student can retake, but if they passed then no)
        - if courses have prerequisites, the student must've passed those before taking the new course
    
    - the student then picks a course they want to take
        - if its a course they have previously withdrawn from/failed, take that course out of the prev courses
    - confirmation message
    - course is added and the grade is assigned null
    
    REMOVING:
    - student is asked which course they want to withdraw from based on initial list
    - confirmation message
    - the course is withdrawn from and placed into the previous courses file with a withdrawn grade
     */
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
                        StudentAddCourse addCourse = new StudentAddCourse(currentStudent);
                        addCourse.validateUserInput();
                        validInput = true;
                        break;
                    case "2":
                        //call remove course class
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
