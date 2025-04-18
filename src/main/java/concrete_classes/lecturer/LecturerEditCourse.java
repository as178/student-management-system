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
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

/**
 *
 * @author williamniven
 */
public class LecturerEditCourse implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;

    public LecturerEditCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    @Override
    public void showMenu() {
        System.out.println("> Major: " + currentCourse.getCourseMajor());
        System.out.println("> Prerequisite: " + currentCourse.getCoursePrerequisite());
        System.out.println("> Estimated Hours: " + currentCourse.getCourseEstimatedHours());
        System.out.println("> Lecturer: " + currentCourse.getCourseLecturer());
        System.out.println("> Description:\n" + WordUtils.wrap(currentCourse.getCourseDescription(), 46));
        System.out.println();
        System.out.println("Selected course: " + currentCourse.getCourseId() + " - " + currentCourse.getCourseName());
        System.out.println();
        System.out.println("1) Edit Course Name");
        System.out.println("2) Edit estimated course hours");
        System.out.println("3) Edit Course Description");
        System.out.println("b - Go Back (Course Info)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Edit Course");
    }

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        outerLoop:
        while (true) {
            this.showHeader();
            this.showMenu();

            FilesManager.allCourses.remove(currentCourse);

            String userInput = scan.nextLine();

            if (NavigationUtil.backOrExit(userInput)) {
                FilesManager.allCourses.add(currentCourse);
                FilesManager.writeAllCourses();
                return "b";
            }

            boolean validInput = false;
            //inner loop
            while (!validInput) {
                switch (userInput) {
                    case "1":
                        System.out.print("New course name (b - Go Back (Edit Course) x - Exit): "); //name limit of 46 chars
                        String nameInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(nameInput)) {
                            continue outerLoop;
                        }
                        currentCourse.setCourseName(nameInput);
                        validInput = true;
                        break;

                    case "2":
                        while (true) {
                            System.out.print("New estimated hours ((b - Go Back (Edit Course) x - Exit): ");
                            String hoursInput = scan.nextLine();
                            if (NavigationUtil.backOrExit(hoursInput)) {
                                continue outerLoop;
                            }
                            try {
                                int estimatedHours = Integer.parseInt(hoursInput);
                                if (estimatedHours <= 0) {
                                    System.out.println("Estimated hours must be greater than 0."); //or no bigger than 150
                                } else {
                                    currentCourse.setCourseEstimatedHours(estimatedHours);
                                    validInput = true;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }
                        break;

                    case "3":
                        System.out.print("New description (b - Go Back (Edit Course) x - Exit): "); //character limit or course desc cant be empty?
                        String descInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(descInput)) {
                            continue outerLoop;
                        }
                        currentCourse.setCourseDescription(descInput);
                        validInput = true;
                        break;

                    default:
                        HeadersUtil.printHeader("Please pick a valid option.");
                        this.showMenu();
                        userInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(userInput)) {
                            return "b"; 
                        }
                }
            }
            FilesManager.allCourses.add(currentCourse);
            FilesManager.writeAllCourses();
        }
    }
}
