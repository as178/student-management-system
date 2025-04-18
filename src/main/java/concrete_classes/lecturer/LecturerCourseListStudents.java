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
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author williamniven
 */
public class LecturerCourseListStudents implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;
    private HashMap<Integer, String> studentGrades;
    private HashMap<Integer, Student> enrolledStudents;

    public LecturerCourseListStudents(Course currentCourse) {
        this.currentCourse = currentCourse;
        studentGrades = FilesManager.loadEnrolledStudentsGrades(currentCourse.getCourseId());
        enrolledStudents = FilesManager.loadEnrolledStudents(studentGrades);
    }

    @Override
    public void showMenu() {
        if (studentGrades.size() == 0) {
            System.out.println("> No students currently enrolled . . .\nb - Go Back (Course Info)\nx - Exit");

        } else {
            System.out.println("[ Student ID || Student Name, Student's Grade ]");

            for (Map.Entry<Integer, String> entry : studentGrades.entrySet()) {
                Integer id = entry.getKey();
                String grade = entry.getValue();

                Student student = enrolledStudents.get(id);

                if (student != null) {
                    System.out.println(">  "+id + "  || " + student.getFirstName() + " " + student.getLastName() + ", " + grade);
                } else {
                    HeadersUtil.printHeader("> Student Not Found . . .");
                }
            }
            HeadersUtil.printHeader("(See below for further options.)");
            System.out.println("b - Go Back (Course Options)\nx - Exit");
        }
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Currently Enrolled Students for:",
                currentCourse.getCourseId() + ", " + currentCourse.getCourseName(),
                "Please enter a Student's ID to",
                "assign their grade.");
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

            boolean validInput = false;
            while (!validInput) {
                try {
                    int studentID = Integer.parseInt(userInput);
                    if (enrolledStudents.containsKey(studentID) && studentGrades.containsKey(studentID)) {
                        LecturerEditStudentGrade editStudentGrade = new LecturerEditStudentGrade(
                                currentCourse,
                                enrolledStudents.get(studentID),
                                Integer.parseInt(studentGrades.get(studentID)),
                                studentGrades
                        );
                        editStudentGrade.validateUserInput();
                        validInput = true;
                        break;
                    } else {
                        HeadersUtil.printHeader("Please pick a valid student.");
                        this.showMenu();
                        userInput = scan.nextLine();

                        if (NavigationUtil.backOrExit(userInput)) {
                            return "b";
                        }
                    }
                } catch (NumberFormatException e) {
                    HeadersUtil.printHeader("Please enter a valid ID.");
                    this.showMenu();
                    userInput = scan.nextLine();

                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    }
                }
            }
        }
    }
}
