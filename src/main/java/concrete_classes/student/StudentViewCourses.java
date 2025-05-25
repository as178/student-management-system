/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.GradesUtil;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays detailed information about the courses the student is
 * currently taking (and if they choose so, the courses they have previously
 * taken as well).
 *
 */
public class StudentViewCourses implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;

    public StudentViewCourses(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Please see below for information about",
                "your current courses, or see further", "options displayed.");
    }

    @Override
    public void showMenu() {
        if (currentStudent.getEnrolledCourses().isEmpty()) {
            System.out.println("> No courses being taken at this time . . .");
        } else {
            //iterate through student's enrolledCourses hashmap
            for (String courseId : currentStudent.getEnrolledCourses().keySet()) {

                //iterate through the allCourses hashset
                for (Course currentCourse : FilesManager.allCourses) {

                    //when student's course is found in the hashset
                    //print information about the course
                    if (currentCourse.getCourseId().equals(courseId)) {

                        HeadersUtil.printHeader(currentCourse.getCourseId(), currentCourse.getCourseName());

                        Float courseGrade = currentStudent.getEnrolledCourses().get(courseId);
                        System.out.println("> Your Grade: " + GradesUtil.convertFloatToGrade(courseGrade));

                        System.out.println("> Major: " + currentCourse.getCourseMajor());
                        System.out.println("> Prerequisite: " + currentCourse.getCoursePrerequisite());
                        System.out.println("> Estimated Hours: " + currentCourse.getCourseEstimatedHours());
                        System.out.println("> Lecturer: " + currentCourse.getCourseLecturer());
                        System.out.println("> Description:\n"
                                + WordUtils.wrap(currentCourse.getCourseDescription(), 46));
                    }
                }
            }
        }

        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - View Previously Taken Courses\nb - Go Back (My Academic Details)\nx - Exit");
    }

    //separate method for displaying details about student's
    //previous courses, similar logic to method above
    public void showPreviouscourses() {
        HeadersUtil.printHeader("Please see below for information about",
                "your previous courses, or see further", "options displayed.");

        if (currentStudent.getPreviousCourses().isEmpty()) {
            System.out.println("> No previous courses exist yet . . .");
        } else {
            for (String courseId : currentStudent.getPreviousCourses().keySet()) {
                for (Course currentCourse : FilesManager.allCourses) {
                    if (currentCourse.getCourseId().equals(courseId)) {

                        HeadersUtil.printHeader(currentCourse.getCourseId(), currentCourse.getCourseName());

                        Float courseGrade = currentStudent.getPreviousCourses().get(courseId);
                        System.out.println("> Your Grade: " + GradesUtil.convertFloatToGrade(courseGrade));

                        System.out.println("> Major: " + currentCourse.getCourseMajor());
                        System.out.println("> Prerequisite: " + currentCourse.getCoursePrerequisite());
                        System.out.println("> Estimated Hours: " + currentCourse.getCourseEstimatedHours());
                        System.out.println("> Lecturer: " + currentCourse.getCourseLecturer());
                        System.out.println("> Description:\n"
                                + WordUtils.wrap(currentCourse.getCourseDescription(), 46));
                    }
                }
            }
        }

        HeadersUtil.printHeader("Enter in anything or simply press",
                "'Enter' to load your current courses again,", "or pick an option below.");
        System.out.println("b - Go Back (My Academic Details)\nx - Exit");
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
                        this.showPreviouscourses(); //show details about previous courses
                        userInput = scan.nextLine().trim(); //allow student to enter when ready
//                        if (NavigationUtil.backOrExit(userInput)) { //check if they want to go back or exit
//                            return "b";
//                        }
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
