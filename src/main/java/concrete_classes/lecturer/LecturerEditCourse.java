/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import objects.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.ValidationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class enables lecturer to edit their courses' details,
 * and reflects these changes within the allCourses.txt file.
 *
 */
public class LecturerEditCourse implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;

    public LecturerEditCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    //shows info about the current course object
    @Override
    public void showMenu() {
        System.out.println("> Major: " + currentCourse.getCourseMajor());
        System.out.println("> Prerequisite: " + currentCourse.getCoursePrerequisite());
        System.out.println("> Estimated Hours: " + currentCourse.getCourseEstimatedHours());
        System.out.println("> Lecturer: " + currentCourse.getCourseLecturer());
        //System.out.println("> Description:\n" + WordUtils.wrap(currentCourse.getCourseDescription(), 46));
        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - Edit Course Name");
        System.out.println("2 - Edit Estimated Course Hours");
        System.out.println("3 - Edit Course Description");
        System.out.println("b - Go Back (Course Options)\nx - Exit");
    }

    //prints current course id and name
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Edit Course Information for:",
                currentCourse.getCourseId() + ", " + currentCourse.getCourseName());
    }

    
    /*
    The following method:
    - allows lecturer to select and modify course name, esimated hours, and description
        - course name has to be between 4 - 46 characters long
        - esimated hours between 1 - 150 hours
        - descriptoin between 5 - 70 characters
    - after changes are made, the course object is added to the allCourses hashset
      to update the course information
    - and these changes are reflected within the allCourses.txt file
    
    The primary logic behind the validateUserInput method remains
    the same with an outer/inner loop combination for dashboard
    re-displaying and re-prompting in case of invalid input.
    Again the user is always given the option to go back or exit.
     */
    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        outerLoop:
        while (true) {
            this.showHeader();
            this.showMenu();

            FilesManager.allCourses.remove(currentCourse);

            String userInput = scan.nextLine().trim();

//            //save currentCourse before going back
//            if (NavigationUtil.backOrExit(userInput)) {
//                FilesManager.allCourses.add(currentCourse);
//                FilesManager.writeAllCourses();
//                return "b";
//            }

            boolean validInput = false;
            //inner loop
            while (!validInput) {
                switch (userInput) {
                    case "1":
                        HeadersUtil.printHeader("Please enter the new course name:");
                        System.out.println("b - Go Back (Edit Course)\nx - Exit");
                        String nameInput = scan.nextLine();
//                        if (NavigationUtil.backOrExit(nameInput)) {
//                            continue outerLoop;
//                        }

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
//                            if (NavigationUtil.backOrExit(hoursInput)) {
//                                continue outerLoop;
//                            }
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
//                        if (NavigationUtil.backOrExit(descInput)) {
//                            continue outerLoop;
//                        }

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

                    default: //invalid input ==> re-prompt
                        HeadersUtil.printHeader("Invalid option.");
                        this.showMenu();
                        userInput = scan.nextLine().trim();
//                        if (NavigationUtil.backOrExit(userInput)) {
//                            return "b";
//                        }
                }
            }
            //save currentCourse within the hashset
            FilesManager.allCourses.add(currentCourse);
            //update the allCourses.txt file
            FilesManager.writeAllCourses();
        }
    }
}
