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

    public LecturerCourseListStudents(Course currentCourse) {
        this.currentCourse = currentCourse;
        this.studentGrades = new HashMap<Integer, String>();
        FilesManager.readAllStudents();
    }

    @Override
    public void showMenu() {

        this.studentGrades.clear();
        this.studentGrades = FilesManager.readEnrolledStudentsGrades(currentCourse.getCourseId());

        if (studentGrades.size() == 0) {
            System.out.println("> No students currently enrolled . . .\nb - Go Back (Course Info)\nx - Exit");

        } else {
            System.out.println("[ Student ID || Student Name, Student's Grade ]");

            for (Map.Entry<Integer, String> entry : studentGrades.entrySet()) {
                Integer id = entry.getKey();
                String grade = (entry.getValue() != null) ? entry.getValue() : "N/A";

                Student student = (Student) FilesManager.currentUsers.get(id.toString());

                if (student != null) {
                    System.out.println(">  " + id + "  || " + student.getFirstName() + " " + student.getLastName() + ", " + grade);
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
                "configure their grade and see",
                "further options.");
    }

    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

        while (true) {

            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine();

            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }
                try {
                    Integer studentID = Integer.valueOf(userInput);

                    if (FilesManager.currentUsers.containsKey(studentID.toString()) && studentGrades.containsKey(studentID)) {

                        Integer studentParsedGrade;
                        try {
                            studentParsedGrade = Integer.parseInt(studentGrades.get(studentID));
                        } catch (NumberFormatException | NullPointerException e) {
                            studentParsedGrade = null;
                        }

                        LecturerEditStudentGrade editStudentGrade = new LecturerEditStudentGrade(
                                currentCourse,
                                (Student) FilesManager.currentUsers.get(studentID.toString()),
                                studentParsedGrade,
                                studentGrades
                        );

                        editStudentGrade.validateUserInput();
                        validInput = true;
                    } else {
                        HeadersUtil.printHeader("Please pick a valid student.");
                        this.showMenu();
                        userInput = scan.nextLine();
                    }
                } catch (NumberFormatException e) {
                    HeadersUtil.printHeader("Please enter a valid ID.");
                    this.showMenu();
                    userInput = scan.nextLine();
                }
            }
        }
    }
}
