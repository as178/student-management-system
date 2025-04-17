/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.students.Student;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author williamniven
 */
public class LecturerEditStudentGrade implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;
    private Student currentStudent;
    private int currentStudentGrade;
    HashMap<Integer, String> studentGrades;

    public LecturerEditStudentGrade(Course currentCourse, Student currentStudent, int currentStudentGrade, HashMap<Integer, String> studentGrades) {
        this.currentCourse = currentCourse;
        this.currentStudent = currentStudent;
        this.currentStudentGrade = currentStudentGrade;
        this.studentGrades = studentGrades;
    }

    @Override
    public void showMenu() {
        System.out.println("Set grade for " + currentStudent.getId() + ", " + currentStudent.getFirstName() + " " + currentStudent.getLastName());
        System.out.println("Current Grade:" + currentStudentGrade);
        System.out.print("Enter updated score (b - Go Back (Enrolled Students) x - Exit):  ");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Edit Student Grade");
    }

    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

        this.showHeader();

        while (true) {
            this.showMenu();
            String userInput = scan.nextLine();

            if (NavigationUtil.backOrExit(userInput)) {
                return "b";
            }
            try {
                int newScore = Integer.parseInt(userInput);
                if (newScore >= 0 && newScore <= 100) {
                    studentGrades.put(currentStudent.getId(), userInput);
                    FilesManager.writeEnrolledStudentsGrades(currentStudent.getId(), currentCourse.getCourseId(), userInput);
                    HeadersUtil.printHeader("Grade updated successfully.");
                    return "b";
                } else {
                    HeadersUtil.printHeader("Please enter a score between 0 - 100.");
                    this.showMenu();
                    userInput = scan.nextLine();
                }
            } catch (NumberFormatException e) {
                HeadersUtil.printHeader("Invalid input. Please enter a number.");
                this.showMenu();
                userInput = scan.nextLine();
            }
        }
    }
}
