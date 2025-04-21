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
 * This class displays all courses in a student's major and shows
 * them which of those courses they are eligible to take. The student
 * can choose to enroll or go back. They may not take more than 8 courses
 * at a time.
 * 
 */
public class StudentEnrollCourse implements DashboardInterface, HeaderInterface, InputValidationInterface {
    
    private Student currentStudent;
    
    //list of courses the student is eligible to take; indexed list used for option logic
    private ArrayList<Course> availableCourses;
    
    //list of all courses in the student's major
    private ArrayList<Course> majorCourses; 
    
    public StudentEnrollCourse(Student currenStudent) {
        this.currentStudent = currenStudent;
        this.availableCourses = new ArrayList<Course>();
        this.majorCourses = getMajorCourses();
    }
    
    @Override
    public void showMenu() {
        //shows all courses in major first, along with their prerequisites
        System.out.println("[ Course Code ---> Course Prerequisite ]");
        for (int i = 0; i < majorCourses.size(); i++) {
            System.out.println(">   " + majorCourses.get(i).getCourseId()
                    + "   --->   " + majorCourses.get(i).getCoursePrerequisite());
        }
        //shows courses student is eligible to enroll into afterwards
        HeadersUtil.printHeader("The courses you are currently eligible",
                "to take are listed below.",
                "(See further options below.)");
        if (this.availableCourses.isEmpty()) {
            System.out.println("> No available courses yet . . .");
            HeadersUtil.printHeader("Please pick one of the following:");
        } else {
            for (int i = 0; i < availableCourses.size(); i++) {
                System.out.println((i + 1) + " - " + availableCourses.get(i).getCourseId()
                        + ", " + availableCourses.get(i).getCourseName());
            }
            HeadersUtil.printHeader("Which course would you like to enroll in?");
        }
        System.out.println("b - Go Back (Change Your Courses)\nx - Exit");
    }
    
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Courses From Major",
                "The following are all the",
                "courses available in your major.");
    }
    
    //separate method for populating the availableCourses list
    public void getAvailableCourses() {
        //clear the list first, in case of updates
        this.availableCourses.clear();
        
        //iterate thorugh the allCourses hashset
        for (Course course : FilesManager.allCourses) { //add course to availableCourses list IF:
            if (course.getCourseMajor().equals(currentStudent.getMajor()) //course is in the student's major
                    && !currentStudent.getEnrolledCourses().containsKey(course.getCourseId()) //student isn't already enrolled in the course
                    && (!currentStudent.getPreviousCourses().containsKey(course.getCourseId()) //student hasn't taken the course yet
                    || currentStudent.getPreviousCourses().get(course.getCourseId()) < 49.5f)) { //or if they have, they withdrew from it/failed

                if (course.getCoursePrerequisite().equals("None")) { //course has no prerequisites
                    availableCourses.add(course);
                } else if (currentStudent.getPreviousCourses().containsKey(course.getCoursePrerequisite()) //or if it does, ensure:
                        && currentStudent.getPreviousCourses().get(course.getCoursePrerequisite()) >= 49.5f) { //student passed them & didn't withdraw
                    availableCourses.add(course);
                }
            }
        }
    }
    
    //separate method for populating the majorCourses list
    // - doesn't populate the list directly like the method above,
    //   instead it returns an arraylist of courses, because
    //   this list won't change/get updated while the student is logged in 
    public ArrayList<Course> getMajorCourses() {
        
        ArrayList<Course> coursesList = new ArrayList<Course>();

        //add courses to list if they have the same major as the student
        for (Course course : FilesManager.allCourses) {
            if (course.getCourseMajor().equals(currentStudent.getMajor())) {
                coursesList.add(course);
            }
        }
        
        return coursesList;
    }
    
    /*
    This validateUserInput method:
    - checks if the student is already taking the maximum number of courses
        - if they are, a descriptive notification is shown and the student
          is returned to the previous dashboard
    - else, the method loads the available courses and allows the student
      to select the course they want to enroll into
    */
    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);
        
        while (true) {
            if (currentStudent.getEnrolledCourses().size() >= 8) {
                HeadersUtil.printHeader("You have reached the maximum number of",
                        "courses a student can take at a time.",
                        "You may wish to withdraw from one of your",
                        "current courses, or complete your",
                        "current enrollments before enrolling",
                        "into more courses.",
                        "('Enter' to continue, or enter in 'x' to Exit.)");
                String userInput = scan.nextLine().trim();
                //check if the student wants to exit the program,
                //otherwise they will be taken, to the previous dashboard by default
                NavigationUtil.checkExit(userInput);
            } else {
                
                this.getAvailableCourses();
                this.showHeader();
                this.showMenu();
                String userInput = scan.nextLine().trim();
                
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }
                
                //validation loop to check if the option the student has selected is valid
                //based on the availableCourses list
                // - invalid input ==> re-prompt
                // - always checking if the student wants to go back or exit
                while (!ValidationUtil.checkIntegerRange(userInput, 1, this.availableCourses.size())) {
                    HeadersUtil.printHeader("Invalid input, please pick a valid option.");
                    this.showMenu();
                    userInput = scan.nextLine().trim();
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    }
                }
                
                //if the userInput is valid, parse into a validOption integer
                int validOption = Integer.parseInt(userInput);
                
                //based on this option (minused by 1), get the course the student
                //wants to enroll into from the availableCourses list
                Course chosenCourse = this.availableCourses.get(validOption - 1);
                
                //display a confirmation message to the student, displaying the selected course id
                HeadersUtil.printHeader("You have selected " + chosenCourse.getCourseId() + ",",
                        "proceed with enrollment?");
                System.out.println("y - Yes, Enroll Me\nb - Go Back (Available Courses)\nx - Exit");
                userInput = scan.nextLine().trim();
                
                //validate the final choice made by student
                boolean validInput = false;
                while (!validInput) {
                    
                    //check if they want to go back or exit
                    if (NavigationUtil.backOrExit(userInput)) {
                        return "b";
                    
                    //otherwise, if they confirmed the enrollment
                    } else if (userInput.equalsIgnoreCase("y")) {
                        
                        //if the course the student wants to enroll into is in their previousCourses
                        //hashmap, remove it
                        if (currentStudent.getPreviousCourses().containsKey(chosenCourse.getCourseId())) {
                            currentStudent.getPreviousCourses().remove(chosenCourse.getCourseId());
                        }
                        
                        //put the course the student wants to take into their enrolledCourses hashmap
                        currentStudent.getEnrolledCourses().put(chosenCourse.getCourseId(), null);
                        
                        //reflect the changes within studentsEnrolledCourses.txt and studentsPreviousCourses.txt
                        FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsEnrolledCoursesFile,
                                currentStudent.getEnrolledCourses(), false);
                        FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsPreviousCoursesFile,
                                currentStudent.getPreviousCourses(), false);
                        
                        //print a success message
                        HeadersUtil.printHeader("Enrollment successful!", "Welcome to " + chosenCourse.getCourseId() + "!");
                        
                        //and confirm the valid input
                        validInput = true;
                        
                    //otherwise, re-prompt the student
                    } else {
                        HeadersUtil.printHeader("Invalid input, please pick one of the following:");
                        System.out.println("y - Yes, Enroll Me\nb - Go Back (Available Courses)\nx - Exit");
                        userInput = scan.nextLine().trim();
                    }
                }
            }
            //go back by default after if statement is finished
            return "b";
        }
    }
}