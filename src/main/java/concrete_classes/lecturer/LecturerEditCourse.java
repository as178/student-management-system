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
        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - Edit Course Name");
        System.out.println("2 - Edit Estimated Course Hours");
        System.out.println("3 - Edit Course Description");
        System.out.println("b - Go Back (Course Options)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Edit Course Information for:",
                currentCourse.getCourseId() + ", " + currentCourse.getCourseName());
    }

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();

            FilesManager.allCourses.remove(currentCourse);

            String userInput = scan.nextLine();

            if (NavigationUtil.backOrExit(userInput)) {
                FilesManager.allCourses.add(currentCourse);
                FilesManager.saveAllCourses();
                return "b";
            }

            boolean validInput = false;

            while (!validInput) {
                switch (userInput) {
                    case "1":
                        HeadersUtil.printHeader("Please enter the new course name:");
                        System.out.println("b - Go Back (Edit Course)\nx - Exit");
                        String nameInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(nameInput)) {
                            return this.validateUserInput();
                        }
                        currentCourse.setCourseName(nameInput);
                        HeadersUtil.printHeader("Course name saved successfully!");
                        validInput = true;
                        break;

                    case "2":
                        HeadersUtil.printHeader("Please enter the new estimated hours:");
                        while (true) {
                            System.out.println("b - Go Back (Edit Course)\nx - Exit");
                            String hoursInput = scan.nextLine();
                            if (NavigationUtil.backOrExit(hoursInput)) {
                                return this.validateUserInput();
                            }
                            try {
                                int estimatedHours = Integer.parseInt(hoursInput);
                                if (estimatedHours <= 0) {
                                    HeadersUtil.printHeader("Estimated hours must be greater than 0.");
                                } else {
                                    currentCourse.setCourseEstimatedHours(estimatedHours);
                                    HeadersUtil.printHeader("Estimated hours saved successfully!");
                                    validInput = true;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                HeadersUtil.printHeader("Invalid input. Please enter a number.");
                            }
                        }
                        break;

                    case "3":
                        HeadersUtil.printHeader("Please enter the new", "course description:");
                        System.out.println("b - Go Back (Edit Course)\nx - Exit");
                        String descInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(descInput)) {
                            return this.validateUserInput();
                        }
                        currentCourse.setCourseDescription(descInput);
                        HeadersUtil.printHeader("Course description saved successfully!");
                        validInput = true;
                        break;

                    default:
                        HeadersUtil.printHeader("Invalid option.");
                        this.showMenu();
                        userInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(userInput)) {
                            break;
                        }
                }
            }
            FilesManager.allCourses.add(currentCourse);
            FilesManager.saveAllCourses();
        }
    }
}
