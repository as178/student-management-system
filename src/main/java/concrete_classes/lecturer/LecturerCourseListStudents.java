/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.student.Student;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays students which are in a particular course,
 * along with further options open to the lecturer.
 * 
 */
public class LecturerCourseListStudents implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;
    private HashMap<Integer, String> studentGrades;

    public LecturerCourseListStudents(Course currentCourse) {
        this.currentCourse = currentCourse;
        this.studentGrades = new HashMap<Integer, String>();
        FilesManager.readAllStudents(); //load up all students into the currentUsers hashmap
    }

    //prints message if no students are found 
    //prints out the ID, full name, and grade of the student
    @Override
    public void showMenu() {

        //clear the list in order to update each time the menu is shown
        this.studentGrades.clear();
        //get the students' grades for this particular course
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

    //show course id and name
    //tell the user to enter a students ID
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Currently Enrolled Students for:",
                currentCourse.getCourseId() + ", " + currentCourse.getCourseName(),
                "Please enter a Student's ID to",
                "configure their grade and see",
                "further options.");
    }

    
    
    /*
    The following validation method:
    - takes ID input
    - checks if target ID exsits in FilesManager currentUsers and in studentGrades
    - passes the course object, casted student object from FilesManager currentUsers,
      the student's grade, and students grade hashmap into the
      LecturerEditStudentGrade class, which will enable lecturer to configure a student's
      grade
    
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
                try {
                    Integer studentID = Integer.valueOf(userInput);

                    if (FilesManager.currentUsers.containsKey(studentID.toString()) && studentGrades.containsKey(studentID)) {
                        
                        Integer studentParsedGrade;
                        //parsing grade with this block of code to ensure no bugs
                        //occur when trying to assign grades to students that currently
                        //have "null" as their grade
                        try {
                            studentParsedGrade = Integer.parseInt(studentGrades.get(studentID));
                        } catch (NumberFormatException | NullPointerException e) {
                            studentParsedGrade = null;
                        }

                        LecturerEditStudentGrade editStudentGrade = new LecturerEditStudentGrade(
                                currentCourse, //course object
                                (Student) FilesManager.currentUsers.get(studentID.toString()), //casted student object
                                studentParsedGrade, //student's grade
                                studentGrades //hashmap of students grade
                        );

                        editStudentGrade.validateUserInput(); //call the class to edit student's grade
                        validInput = true; //confirm valid input 
                    } else {
                        //otherwise provide a valid student id
                        HeadersUtil.printHeader("Please pick a valid student.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
                    }
                //if the user doesn't enter a number
                } catch (NumberFormatException e) {
                    HeadersUtil.printHeader("Please enter a valid ID.");
                    this.showMenu();
                    userInput = scan.nextLine().trim();
                }
            }
        }
    }
}
