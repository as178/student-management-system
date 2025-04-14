/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573)
 */
public class StudentModifyMajor implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;
    private ArrayList<String> listOfMajors;

    public StudentModifyMajor(Student currentStudent) {
        this.currentStudent = currentStudent;
        this.listOfMajors = new ArrayList<String>();
        FilesManager.readAllMajors();
        for (String major : FilesManager.allMajors) {
            if (!currentStudent.getMajor().equals(major)) {
                listOfMajors.add(major);
            }
        }
    }

    // - list the available majors, excluding the one student is already taking
    // - do some sort index selection, so that it does not matter how many
    //majors we add or remove, its dynamic
    // - prior to user choosing a new major, give alert that says all their
    //current courses will be wiped, moved to previous courses and assigned the
    //-1 "withdrawn" grade
    // - if they're ok with it, proceed and save their choice
    // - show message saying "to add your courses go back > select change courses
    @Override
    public void showMenu() {
        for (int i = 0; i < listOfMajors.size(); i++) {
            System.out.println((i + 1) + " - " + listOfMajors.get(i));
        }
        System.out.println("b - Go Back\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Change Your Major",
                "Please pick one of the available majors", "below or see further options displayed.");
    }

    //Work in progress . . .
    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine().trim();
            
            if (NavigationUtil.backOrExit(userInput)) {
                return "b";
            }

            HeadersUtil.printHeader("Changing your major will withdraw you",
                    "from all your current courses.",
                    "Your grades will appear as 'Withdrawn' and",
                    "won't count towards your GPA.",
                    "Are you sure you want to proceed?");
            System.out.println("y - Yes, Change My Major\nb - Go Back\nx - Exit");
            userInput = scan.nextLine();

            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                } else if (userInput.equalsIgnoreCase("y")) {
                    validInput = true;
                } else {
                    HeadersUtil.printHeader("Invalid input, please pick a valid option.");
                    userInput = scan.nextLine();
                }
            }

            int userChoice = Integer.parseInt(userInput);
            if (userChoice >= 1 && userChoice <= this.listOfMajors.size()) {
                String newMajor = this.listOfMajors.get(userChoice - 1);
                currentStudent.setMajor(newMajor);
            }
        }
    }
}