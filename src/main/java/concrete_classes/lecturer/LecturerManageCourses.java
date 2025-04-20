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

    private HashMap<Integer, Course> courses;

    //constructor that takes currentLectuer object input
    //readLecturerCourses loads hashmap in lectuer object with an idex and Course object
    //loads that hashmap into local courses hashmap
    public LecturerManageCourses(Lecturer currentLecturer) {
        FilesManager.readLecturerCourses(currentLecturer);
        FilesManager.readAllCourses();
        courses = currentLecturer.getCoursesTaught();
    }

    //prints the courses that the currentLectuer object teaches with index - course name
    //if not courses assigned then message is printed
    @Override
    public void showMenu() {

        if (courses.isEmpty()) {
            System.out.println("> No assigned courses . . .");
            return;
        }

        for (Map.Entry<Integer, Course> entry : courses.entrySet()) {
            Integer key = entry.getKey();
            Course course = entry.getValue();

            System.out.println(key + " - " + course.getCourseId() + ", " + course.getCourseName());
        }
        System.out.println("b - Go Back (Lecturer Dashboard)\nx - Exit");
    }

    //header telling user to select a course 
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Manage My Courses",
                "Please select a course to manage,",
                "or see below for further options.");
    }

    //displayes header and menu options 
    //method for user slecting a course from the local hashmap
    //user selectes course by index (key) 
    //the selected course object is passed into LecturerCourseOptions class
    //calling validateUserInput method jumps into that dashboard
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
                    int courseKey = Integer.parseInt(userInput);
                    if (courses.containsKey(courseKey)) {
                        LecturerCourseOptions courseInfo = new LecturerCourseOptions(courses.get(courseKey));
                        courseInfo.validateUserInput();
                        validInput = true;
                    } else {
                        HeadersUtil.printHeader("Please pick a valid option.");
                        this.showMenu();
                        userInput = scan.nextLine();
                    }
                } catch (NumberFormatException e) {
                    HeadersUtil.printHeader("Please enter a valid number.");
                    this.showMenu();
                    userInput = scan.nextLine();
                }
            }
        }
    }
}