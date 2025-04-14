/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.GradesUtil;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public class StudentViewAcademicDetails implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;

    public StudentViewAcademicDetails(Student currentStudent) {
        this.currentStudent = currentStudent;
        FilesManager.readEnrolledCourses(currentStudent);
        FilesManager.readPreviousCourses(currentStudent);
        FilesManager.readAllCourses();
    }

    @Override
    public void showMenu() {
        System.out.println("My GPA: "+ String.format("%.2f", currentStudent.getGPA()));
        System.out.println("My Major: " + currentStudent.getMajor());
        System.out.println("My Current Courses & Grades (Maximum of 8): ");

        if (currentStudent.getEnrolledCourses().isEmpty()) {
            System.out.println("> No courses being taken at this time . . .");
        } else {
            System.out.println("[ Course Code || Grade ]");
            for (HashMap.Entry<String, Float> entry : currentStudent.getEnrolledCourses().entrySet()) {
                System.out.println(">   " + entry.getKey() + "   ||  "
                        + GradesUtil.convertFloatToGrade(entry.getValue()));
            }
        }

        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - View More (Course Details)\n2 - Change Major\n"
                + "3 - Change Courses\nb - Go Back\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("My Academic Details");
    }

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine().trim();

            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                switch (userInput) {
                    case "1":
                        StudentViewCourses viewCourses = new StudentViewCourses(currentStudent);
                        viewCourses.validateUserInput();
                        validInput = true;
                        break;
                    case "2":
                        StudentModifyMajor modifyMajor = new StudentModifyMajor(currentStudent);
                        modifyMajor.validateUserInput();
                        validInput = true;
                        break;
                    case "3":
                        //StudentModifyCourses add/remove courses methods                        
                        validInput = true;
                        break;
                    default:
                        HeadersUtil.printHeader("Invalid input, please see below", "for valid options.");
                        this.showMenu();
                        userInput = scan.nextLine();
                }
            }
        }
    }
}