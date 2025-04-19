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
                        HeadersUtil.printHeader("Please enter the new course name:");
                        System.out.println("b - Go Back (Edit Course)\nx - Exit");
                        String nameInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(nameInput)) {
                            continue outerLoop;
                        }

                        if (ValidationUtil.checkIntegerRange(nameInput.length(), 4, 46)) {
                            currentCourse.setCourseName(nameInput);
                            HeadersUtil.printHeader("Course name saved successfully!");
                            validInput = true;
                            break;
                        } else {
                            HeadersUtil.printHeader("Course name cant be empty",
                                    "and must be below 47 characters.");
                        }
                        break;
                    case "2":
                        while (true) {
                            HeadersUtil.printHeader("Please enter the new estimated hours:");
                            System.out.println("b - Go Back (Edit Course)\nx - Exit");
                            String hoursInput = scan.nextLine();
                            if (NavigationUtil.backOrExit(hoursInput)) {
                                continue outerLoop;
                            }
                            if (ValidationUtil.checkIntegerRange(hoursInput, 1, 150)) {
                                currentCourse.setCourseEstimatedHours(Integer.parseInt(hoursInput));
                                HeadersUtil.printHeader("Estimated hours saved successfully!");
                                validInput = true;
                                break;
                            } else {
                                HeadersUtil.printHeader("Estimated hours must be between 1 and 150.");
                            }
                        }
                        break;

                    case "3":
                        HeadersUtil.printHeader("Please enter the new", "course description:");
                        System.out.println("b - Go Back (Edit Course)\nx - Exit");

                        String descInput = scan.nextLine();
                        if (NavigationUtil.backOrExit(descInput)) {
                            continue outerLoop;
                        }

                        if (ValidationUtil.checkIntegerRange(descInput.length(), 5, 70)) {
                            currentCourse.setCourseDescription(descInput);
                            HeadersUtil.printHeader("Course description saved successfully!");
                            validInput = true;
                            break;
                        } else {
                            HeadersUtil.printHeader("Course description must be ",
                                    "between 5 and 70 characters",
                                    "in length.");
                        }
                        break;

                    default:
                        HeadersUtil.printHeader("Invalid option.");
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
