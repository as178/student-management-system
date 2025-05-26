/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import objects.Lecturer;
import objects.Course;
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
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays the courses which the lecturer is currently teaching.
 *
 */
public class LecturerManageCourses implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private HashMap<Integer, Course> courses;

    //constructor that takes currentLectuer object input
    //readLecturerCourses loads hashmap in lectuer object with an index and Course object
    //loads that hashmap into local courses hashmap
    public LecturerManageCourses(Lecturer currentLecturer) {
        FilesManager.readLecturerCourses(currentLecturer);
        FilesManager.readAllCourses();
        //courses = currentLecturer.getCoursesTaught();
    }

    //prints the courses that the currentLectuer object teaches with index - course name
    //if no courses assigned then message is printed
    @Override
    public void showMenu() {
        if (courses.isEmpty()) {
            System.out.println("> No assigned courses . . .");
        } else {
            for (Map.Entry<Integer, Course> entry : courses.entrySet()) {
                Integer key = entry.getKey();
                Course course = entry.getValue();

                System.out.println(key + " - " + course.getCourseId() + ", " + course.getCourseName());
            }
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

    
    /*
    The following method:
    - displays header and menu options 
    - prompts the lecturer to select one of their courses
    - lecturer selects course by index (key) 
    - the selected course object is passed into LecturerCourseOptions class
    - calling validateUserInput method jumps into that dashboard
    
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
                    int courseKey = Integer.parseInt(userInput); //parse user input into int courseKey
                    if (courses.containsKey(courseKey)) { //check if local courses hashmap contains the key
                        LecturerCourseOptions courseInfo = new LecturerCourseOptions(courses.get(courseKey));
                        courseInfo.validateUserInput(); //if so, display further options for that course
                        validInput = true;
                    } else { //otherwise, re-prompt
                        HeadersUtil.printHeader("Please pick a valid option.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
                    }
                } catch (NumberFormatException e) { //invalid input ==> re-prompt
                    HeadersUtil.printHeader("Please enter a valid number.");
                    this.showMenu();
                    userInput = scan.nextLine().trim();
                }
            }
        }
    }
}
