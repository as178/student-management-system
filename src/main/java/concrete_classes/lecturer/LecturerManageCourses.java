/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
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
public class LecturerManageCourses implements DashboardInterface, HeaderInterface, InputValidationInterface {

    //private Lecturer currentLecturer;
    private HashMap<Integer, Course> courses;

    public LecturerManageCourses(Lecturer currentLecturer) {
        //this.currentLecturer = currentLecturer;
        FilesManager.readLecturerCourses(currentLecturer);
        FilesManager.readAllCourses();
        courses = currentLecturer.getCoursesTaught();
    }

    @Override
    public void showMenu() {
        System.out.println("List Of Courses:");

        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }

        for (Map.Entry<Integer, Course> entry : courses.entrySet()) {
            Integer key = entry.getKey();
            Course course = entry.getValue();

            System.out.println(key + ") " + course.getCourseId() + " - " + course.getCourseName());
        }
        System.out.println("b - Go Back (Lecturer Dashboard)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Manage My Courses");
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
                    int courseKey = Integer.parseInt(userInput);
                    if (courses.containsKey(courseKey)) {
                        LecturerCourseInfo courseInfo = new LecturerCourseInfo(courses.get(courseKey));
                        courseInfo.validateUserInput();
                        validInput = true;
                        break;
                    } else {
                        HeadersUtil.printHeader("Please pick a valid option.");
                        this.showMenu();
                        userInput = scan.nextLine();
                        validInput = true;
                        break;
                    }
                } catch (NumberFormatException e) {
                    HeadersUtil.printHeader("Invalid input. Please enter a number.");
                    this.showMenu();
                    userInput = scan.nextLine();
                }
            }
        }
    }
}
