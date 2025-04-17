/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import static concrete_classes.other.ValidationUtil.checkIntegerRange;
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
        System.out.println("Current Score:" + currentStudentGrade);
        System.out.println("b - Go Back (Enrolled Students)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Edit Student Grade",
                "Set grade for " + currentStudent.getId() + ", " + currentStudent.getFirstName() + " " + currentStudent.getLastName(),
                "Enter students new score");
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

            if (checkIntegerRange(userInput, 0, 100)) {
                studentGrades.put(currentStudent.getId(), userInput);
                FilesManager.writeEnrolledStudentsGrades(currentStudent.getId(), currentCourse.getCourseId(), userInput);
                HeadersUtil.printHeader("Score updated successfully.");
                return "b";
            } else {
                HeadersUtil.printHeader("Please enter a number between 0 - 100.");
            }
        }
    }
}
