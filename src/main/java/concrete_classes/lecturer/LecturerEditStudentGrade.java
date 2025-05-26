/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import objects.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.ValidationUtil;
import objects.Student;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class enables the lecturer to edit the grades of students
 * who are currently enrolled in their courses.
 *
 */
public class LecturerEditStudentGrade implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;
    private Student currentStudent;
    private Integer currentStudentGrade;
    HashMap<Integer, String> studentGrades;

    public LecturerEditStudentGrade(Course currentCourse, Student currentStudent,
            Integer currentStudentGrade, HashMap<Integer, String> studentGrades) {
        this.currentCourse = currentCourse;
        this.currentStudent = currentStudent;
        this.currentStudentGrade = currentStudentGrade;
        this.studentGrades = studentGrades;
    }

    @Override
    public void showMenu() {
        System.out.println("s - Sign Off Student\nb - Go Back (Enrolled Students)\nx - Exit");
    }

    //display selected students id and fullname
    @Override
    public void showHeader() {
//        HeadersUtil.printHeader("Configure grade for the following student:",
//                WordUtils.wrap(currentStudent.getId() + ", " + currentStudent.getFirstName() + " " + currentStudent.getLastName(), 46),
//                "Please enter their new grade below,",
//                "or alternatively sign-off the student's",
//                "current grade.");
    }
    
    /*
    The following method:
    - allows lecturer to sign off student from the course with "s"
    - allows lecturer to set a new grade for the student 
    
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

            while (true) {
//                if (NavigationUtil.backOrExit(userInput)) {
//                    return "b";
//                }

                if (userInput.equalsIgnoreCase("s")) {
//                    HeadersUtil.printHeader("Signing Off:",
//                            WordUtils.wrap(currentStudent.getId() + ", " + currentStudent.getFirstName()
//                                    + " " + currentStudent.getLastName(), 46),
//                            "This will remove the student from",
//                            "your course, and their grade will be",
//                            "transferred to their record.",
//                            "Proceed with sign off?");

                    System.out.print("y - Yes, Sign Off Student\nb - Back (Configure Grade)\nx - Exit\n");
                    userInput = scan.nextLine().trim();

                    boolean validInput = false;
                    while (!validInput) {
//                        if (NavigationUtil.backOrExit(userInput)) {
//                            return "b";
//                        } else if (userInput.equalsIgnoreCase("y")) {
//
//                            //load student's enrolled courses to their enrolledCourses hashmap
//                            FilesManager.readCoursesFile(currentStudent, currentStudent.getEnrolledCourses(),
//                                    FilesManager.studentsEnrolledCoursesFile);
//
//                            //load student's previous courses to their previousCourses hashmap
//                            FilesManager.readCoursesFile(currentStudent, currentStudent.getPreviousCourses(),
//                                    FilesManager.studentsPreviousCoursesFile);
//
//                            //add the current course and student's grade to their previousCourses hashmap
//                            currentStudent.getPreviousCourses().put(currentCourse.getCourseId(), currentStudent.getEnrolledCourses().get(currentCourse.getCourseId()));
//
//                            //remove the same course from the student's enrolledCourses hashmap
//                            currentStudent.getEnrolledCourses().remove(currentCourse.getCourseId());
//
//                            //save changes in both hashmaps to their respective .txt files
//                            FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsEnrolledCoursesFile,
//                                    currentStudent.getEnrolledCourses(), false);
//                            FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsPreviousCoursesFile,
//                                    currentStudent.getPreviousCourses(), true);
//
//                            HeadersUtil.printHeader("Thank you, the student was", "successfully signed off!");
//                            validInput = true;
//                        } else { //else re-prompt
//                            HeadersUtil.printHeader("Invalid input, please pick one of the following:");
//                            System.out.print("y - Yes, Sign Off Student\nb - Back (Configure Grade)\nx - Exit\n");
//                            userInput = scan.nextLine().trim();
//                        }
                    }
                    return "b";
                    
                } else {
                    //check the range of the new grade is between 0 - 100
                    //save the student's new grade to the studentGrades hashmap
                    //save the student's new grade into studentsEnrolledCourses.txt
                    if (ValidationUtil.checkIntegerRange(userInput, 0, 100)) {
                        studentGrades.put(currentStudent.getId(), userInput);
                        FilesManager.writeEnrolledStudentsGrades(currentStudent.getId(), currentCourse.getCourseId(), userInput);
                        HeadersUtil.printHeader("Grade assigned successfully!");
                        break;
                    } else { //else re-prompt
                        HeadersUtil.printHeader("Please enter a number between 0 - 100,",
                                "or see available options below.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
                    }
                }
            }
        }
    }
}