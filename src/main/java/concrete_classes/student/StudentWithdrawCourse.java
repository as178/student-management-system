/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.ValidationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays all courses a student is currently taking and prompts
 * them to choose which one they want to withdraw from.
 *
 */
public class StudentWithdrawCourse implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;
    private ArrayList<Course> currentCourses;

    public StudentWithdrawCourse(Student currenStudent) {
        this.currentStudent = currenStudent;

        //list of courses the student is currently taking; indexed list used for option logic
        this.currentCourses = new ArrayList<Course>();
    }

    @Override
    public void showMenu() {
        for (int i = 0; i < currentCourses.size(); i++) {
            System.out.println((i + 1) + " - " + currentCourses.get(i).getCourseId()
                    + ", " + currentCourses.get(i).getCourseName());
        }
        HeadersUtil.printHeader("Please select a valid option.");
        System.out.println("b - Go Back (Change Your Courses)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Which course would you", "like to withdraw from?");
    }

    //separate method for loading up the list
    //of courses the student is currently taking
    public void getCurrentCourses() {
        //clear the list first, in case of updates
        this.currentCourses.clear();

        //iterate through the allCourses hashset
        for (Course course : FilesManager.allCourses) {

            //iterate through the student's enrolledCourses hashmap
            for (String currentCourseId : currentStudent.getEnrolledCourses().keySet()) {

                //when student's course is found in the hashset
                //add the course object into the currentCourses arraylist
                if (currentCourseId.equals(course.getCourseId())) {
                    currentCourses.add(course);
                }
            }
        }
    }

    /*
    This validateUserInput method:
    - checks if the student isn't taking any courses at this time
        - if that's true, a descriptive notification is shown and the student
          is returned to the previous dashboard
    - else, the method loads the current courses and allows the student
      to select the course they want to withdraw from
     */
    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            if (currentStudent.getEnrolledCourses().isEmpty()) {
                HeadersUtil.printHeader("You have no current enrollments",
                        "to withdraw from. You may wish to enroll",
                        "in one of the available options",
                        "from the previous menu's",
                        "'1 - Add a Course' option.",
                        "('Enter' to continue, or enter in 'x' to Exit.)");
                String userInput = scan.nextLine().trim();
                //check if the student wants to exit the program,
                //otherwise they will be taken, to the previous dashboard by default
                NavigationUtil.checkExit(userInput);
            } else {

                this.getCurrentCourses();
                this.showHeader();
                this.showMenu();
                String userInput = scan.nextLine().trim();

                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                //validation loop to check if the option the student has selected is valid
                //based on the currentCourses list
                // - invalid input ==> re-prompt
                // - always checking if the student wants to go back or exit
                while (!ValidationUtil.checkIntegerRange(userInput, 1, this.currentCourses.size())) {
                    HeadersUtil.printHeader("Invalid input, please see below", "for valid options.");
                    this.showMenu();
                    userInput = scan.nextLine().trim();
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    }
                }

                //if the userInput is valid, parse into a validOption integer
                int validOption = Integer.parseInt(userInput);

                //based on this option (minused by 1), get the course the student
                //wants to withdraw from, from the currentCourses list
                Course chosenCourse = this.currentCourses.get(validOption - 1);

                //display a confirmation message to the student, displaying the selected course id
                HeadersUtil.printHeader("You have selected " + chosenCourse.getCourseId() + ",",
                        "proceed to withdraw from course?");
                System.out.println("y - Yes, Withdraw From Course\nb - Go Back (Current Courses)\nx - Exit");
                userInput = scan.nextLine().trim();

                //validate the final choice made by student
                boolean validInput = false;
                while (!validInput) {

                    //check if they want to go back or exit
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";

                    //otherwise, if they confirmed the withdrawal
                    } else if (userInput.equalsIgnoreCase("y")) {

                        //remove the chosen course from the student's enrolledCourses hashmap
                        currentStudent.getEnrolledCourses().remove(chosenCourse.getCourseId());
                        
                        //then put the course into the student's previousCourses hashmap
                        //with the grade set to -1 (withdrawn)
                        currentStudent.getPreviousCourses().put(chosenCourse.getCourseId(), -1f);
                        
                        //reflect the changes within studentsEnrolledCourses.txt and studentsPreviousCourses.txt
                        FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsEnrolledCoursesFile,
                                currentStudent.getEnrolledCourses(), false);
                        FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsPreviousCoursesFile,
                                currentStudent.getPreviousCourses(), true);

                        //print a success message
                        HeadersUtil.printHeader("You have been successfully", "removed from " + chosenCourse.getCourseId() + ".");

                        //and confirm the valid input
                        validInput = true;
                        
                    //otherwise, re-prompt the student
                    } else {
                        HeadersUtil.printHeader("Invalid input, please pick one of the following:");
                        System.out.println("y - Yes, Withdraw From Course\nb - Go Back (Current Courses)\nx - Exit");
                        userInput = scan.nextLine().trim();
                    }
                }
            }
            //go back by default after if statement is finished
            return "b";
        }
    }
}
