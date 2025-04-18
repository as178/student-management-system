/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.ValidationUtil;
import concrete_classes.student.Student;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

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
        System.out.println("Current Grade: " + currentStudentGrade);
        System.out.println("b - Go Back (Enrolled Students)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Assign Student's Grade",
                "Set grade for the following student:",
                WordUtils.wrap(currentStudent.getId() + ", " + currentStudent.getFirstName() + " " + currentStudent.getLastName(), 46),
                "Please enter their new grade below.");
    }

    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine();

            if (NavigationUtil.backOrExit(userInput)) {
                return "b";
            }

            if (ValidationUtil.checkIntegerRange(userInput, 0, 100)) {
                studentGrades.put(currentStudent.getId(), userInput);
                FilesManager.writeEnrolledStudentsGrades(currentStudent.getId(), currentCourse.getCourseId(), userInput);
                HeadersUtil.printHeader("Grade assigned successfully!");
                return "b";
            } else {
                HeadersUtil.printHeader("Please enter a number between 0 - 100.");
            }
        }
    }
}
