/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

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
public class StudentAddCourse implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;
    private ArrayList<Course> availableCourses;

    public StudentAddCourse(Student currenStudent) {
        this.currentStudent = currenStudent;
        this.availableCourses = new ArrayList<Course>();
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Available Courses",
                "The courses you are currently eligible",
                "to take are as follows.",
                "(See further options below.)");
    }

    @Override
    public void showMenu() {
        for (int i = 0; i < availableCourses.size(); i++) {
            System.out.println((i + 1) + " - " + availableCourses.get(i).getCourseId()
                    + ", " + availableCourses.get(i).getCourseName());
        }
        HeadersUtil.printHeader("Which course would you like to enroll in?");
        System.out.println("b - Go Back (Change Your Courses)\nx - Exit");
    }

    public void getAvailableCourses() {
        this.availableCourses.clear();

        for (Course course : FilesManager.allCourses) { //add course to list IF:
            if (course.getCourseMajor().equals(currentStudent.getMajor()) //course is in the student's major
                    && !currentStudent.getEnrolledCourses().containsKey(course.getCourseId()) //student isn't already enrolled in the course
                    && (!currentStudent.getPreviousCourses().containsKey(course.getCourseId()) //student hasn't taken the course yet
                    || currentStudent.getPreviousCourses().get(course.getCourseId()) < 49.5f)) { //or if they have, they withdrew from it/failed

                if (course.getCoursePrerequisite().equals("None")) { //course has no prerequisites
                    availableCourses.add(course);
                } else if (currentStudent.getPreviousCourses().containsKey(course.getCoursePrerequisite()) //or if it does, ensure:
                        && currentStudent.getPreviousCourses().get(course.getCoursePrerequisite()) >= 49.5f) { //student passed them & didn't withdraw
                    availableCourses.add(course);
                }
            }
        }
    }

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);
        
        while (true) {
            if (currentStudent.getEnrolledCourses().size() >= 8) {
                HeadersUtil.printHeader("You have reached the maximum number of",
                        "courses a student can take at a time.",
                        "You may wish to withdraw from one of your",
                        "current courses, or complete your",
                        "current enrollments before enrolling",
                        "into more courses.",
                        "('Enter' to continue, or enter in",
                        "'x' to Exit.)");
                String userInput = scan.nextLine();
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }
            } else {

                this.getAvailableCourses();
                this.showHeader();
                this.showMenu();
                String userInput = scan.nextLine().trim();

                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                while (!ValidationUtil.checkIntegerRange(userInput, 1, this.availableCourses.size())) {
                    HeadersUtil.printHeader("Invalid input, please pick a valid option.");
                    this.showMenu();
                    userInput = scan.nextLine();
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    }
                }

                int validOption = Integer.parseInt(userInput);
                Course chosenCourse = this.availableCourses.get(validOption - 1);

                HeadersUtil.printHeader("You have selected " + chosenCourse.getCourseId() + ",",
                        "proceed with enrollment?");
                System.out.println("y - Yes, Enroll Me\nb - Go Back (Available Courses)\nx - Exit");
                userInput = scan.nextLine().trim();

                boolean validInput = false;
                while (!validInput) {
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    } else if (userInput.equalsIgnoreCase("y")) {

                        if (currentStudent.getPreviousCourses().containsKey(chosenCourse.getCourseId())) {
                            currentStudent.getPreviousCourses().remove(chosenCourse.getCourseId());
                        }

                        currentStudent.getEnrolledCourses().put(chosenCourse.getCourseId(), null);

                        FilesManager.updateStudentCourseFile(currentStudent,
                                "src/main/java/text_files/studentsEnrolledCourses.txt",
                                currentStudent.getEnrolledCourses(), false);
                        FilesManager.updateStudentCourseFile(currentStudent,
                                "src/main/java/text_files/studentsPreviousCourses.txt",
                                currentStudent.getPreviousCourses(), true);

                        HeadersUtil.printHeader("Enrollment successful! Welcome to " + chosenCourse.getCourseId() + "!");

                        validInput = true;
                    } else {
                        HeadersUtil.printHeader("Invalid input, please pick one of the following:");
                        System.out.println("y - Yes, Enroll Me\nb - Go Back (Available Courses)\nx - Exit");
                        userInput = scan.nextLine().trim();
                    }
                }
            }
        }
    }
}