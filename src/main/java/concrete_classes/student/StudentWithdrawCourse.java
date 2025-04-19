/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.ValidationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public class StudentWithdrawCourse implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;
    private ArrayList<Course> currentCourses;

    public StudentWithdrawCourse(Student currenStudent) {
        this.currentStudent = currenStudent;
        this.currentCourses = new ArrayList<Course>();
    }

    @Override
    public void showMenu() {
        for (int i = 0; i < currentCourses.size(); i++) {
            System.out.println((i + 1) + " - " + currentCourses.get(i).getCourseId()
                    + ", " + currentCourses.get(i).getCourseName());
        }
        HeadersUtil.printHeader("Please select a valid option.");
        System.out.println("b - Go Back (Change Your Courses)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Which course would you", "like to withdraw from?");
    }

    public void getCurrentCourses() {
        this.currentCourses.clear();
        for (Course course : FilesManager.allCourses) {
            for (String currentCourseId : currentStudent.getEnrolledCourses().keySet()) {
                if (currentCourseId.equals(course.getCourseId())) {
                    currentCourses.add(course);
                }
            }
        }
    }

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            if (currentStudent.getEnrolledCourses().size() == 0) {
                HeadersUtil.printHeader("You have no current enrollments",
                        "to withdraw from. You may wish to enroll",
                        "in one of the available options",
                        "from the previous menu's",
                        "'1 - Add a Course' option.",
                        "('Enter' to continue, or enter in",
                        "'x' to Exit.)");
                String userInput = scan.nextLine();
                NavigationUtil.checkExit(userInput);
            } else {

                this.getCurrentCourses();
                this.showHeader();
                this.showMenu();
                String userInput = scan.nextLine().trim();

                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                while (!ValidationUtil.checkIntegerRange(userInput, 1, this.currentCourses.size())) {
                    HeadersUtil.printHeader("Invalid input, please see below", "for valid options.");
                    this.showMenu();
                    userInput = scan.nextLine();
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    }
                }

                int validOption = Integer.parseInt(userInput);
                Course chosenCourse = this.currentCourses.get(validOption - 1);

                HeadersUtil.printHeader("You have selected " + chosenCourse.getCourseId() + ",",
                        "proceed to withdraw from course?");
                System.out.println("y - Yes, Withdraw From Course\nb - Go Back (Current Courses)\nx - Exit");
                userInput = scan.nextLine().trim();

                boolean validInput = false;
                while (!validInput) {
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    } else if (userInput.equalsIgnoreCase("y")) {

                        currentStudent.getEnrolledCourses().remove(chosenCourse.getCourseId());
                        currentStudent.getPreviousCourses().put(chosenCourse.getCourseId(), -1f);
                        
                        FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsEnrolledCoursesFile,
                                currentStudent.getEnrolledCourses(), false);
                        FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsPreviousCoursesFile,
                                currentStudent.getPreviousCourses(), true);

                        HeadersUtil.printHeader("You have been successfully", "removed from " + chosenCourse.getCourseId() + ".");

                        validInput = true;
                    } else {
                        HeadersUtil.printHeader("Invalid input, please pick one of the following:");
                        System.out.println("y - Yes, Withdraw From Course\nb - Go Back (Current Courses)\nx - Exit");
                        userInput = scan.nextLine().trim();
                    }
                }
            }
            return "b";
        }
    }
}