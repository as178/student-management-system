/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

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
 * This class enables the student to change their major based on a dynamic list
 * of available majors (which excludes the major the student is currently
 * taking).
 *
 */
public class StudentModifyMajor implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;
    private ArrayList<String> listOfMajors;

    public StudentModifyMajor(Student currentStudent) {
        this.currentStudent = currentStudent;

        //arraylist of majors the student can take;
        //indexed elements will be used for dynamic options logic
        this.listOfMajors = new ArrayList<String>();

        //populate the static allMajors hashset
        FilesManager.readAllMajors();
    }

    @Override
    public void showMenu() {
        for (int i = 0; i < listOfMajors.size(); i++) {
            System.out.println((i + 1) + " - " + listOfMajors.get(i));
        }
        System.out.println("b - Go Back (My Academic Details)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Change Your Major",
                "Please pick one of the available majors", "below or see further options displayed.");
    }

    //separate method for loading up the list of
    //majors the student can take (excludes their
    //current major)
    public void getAvailableMajors() {
        //clear the arraylist to reflect updates prior to loading
        this.listOfMajors.clear();

        //add all majors to list except the one the student currently has
        for (String major : FilesManager.allMajors) {
            if (!currentStudent.getMajor().equals(major)) {
                listOfMajors.add(major);
            }
        }
    }

    /*
    This validateUserInput method:
    - displays a list of available majors to the student
      based on the arraylist
    - enables the student to select which major they want
      to switch to
    - always checks if the student wants to go back or
      exit the program
     */
    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.getAvailableMajors();
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine().trim();
//
//            if (NavigationUtil.backOrExit(userInput)) {
//                return "b";
//            }

            //validation loop to check if the option the student has selected is valid
            //based on the listOfMajors list
            // - invalid input ==> re-prompt
            // - always checking if the student wants to go back or exit
            while (!ValidationUtil.checkIntegerRange(userInput, 1, this.listOfMajors.size())) {
                HeadersUtil.printHeader("Invalid input, please pick a valid option.");
                this.showMenu();
                userInput = scan.nextLine().trim();
//                if (NavigationUtil.backOrExit(userInput)) {
//                    return "b";
//                }
            }

            //if the userInput is valid, parse into a validOption integer
            int validOption = Integer.parseInt(userInput);

            //based on this option (minused by 1), get the major the student
            //wants to change to from the listOfMajors
            String chosenMajor = this.listOfMajors.get(validOption - 1);

            //display a confirmation message to the student,
            //informing them that they will be withdrawn from
            //their current courses
            HeadersUtil.printHeader("Changing your major will withdraw you",
                    "from all your current courses.",
                    "Your grades will appear as 'Withdrawn' and",
                    "won't count towards your GPA.",
                    "Are you sure you want to proceed?");
            System.out.println("y - Yes, Change My Major\nb - Go Back (Available Majors)\nx - Exit");
            userInput = scan.nextLine().trim();

            //validate the final choice made by student
            boolean validInput = false;
            while (!validInput) {

//                //check if they want to go back or exit
//                if (NavigationUtil.backOrExit(userInput)) {
//                    validInput = true; //confirm valid input and restart outer loop
//
//                //otherwise, if they confirmed the modification
//                } else if (userInput.equalsIgnoreCase("y")) {
//
//                    //update the student's major to be the new one they chose
//                    currentStudent.setMajor(chosenMajor);
//
//                    //withdraw them from all their current courses
//                    FilesManager.withdrawAllCourses(currentStudent);
//                    
//                    //save the changes to the student
//                    FilesManager.saveCurrentUser(currentStudent);
//
//                    //print success message
//                    HeadersUtil.printHeader("Your major was successfully updated to: ",
//                            chosenMajor + "!",
//                            "To enroll into your new courses enter in 'b'",
//                            "and select '3 - Change Courses'.");
//
//                    //confirm valid input
//                    validInput = true;
//
//                //otherwise, re-prompt the student
//                } else {
//                    HeadersUtil.printHeader("Invalid input, please pick one of the following:");
//                    System.out.println("y - Yes, Change My Major\nb - Go Back (Available Majors)\nx - Exit");
//                    userInput = scan.nextLine().trim();
//                }
            }
        }
    }
}
