/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

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
 * @author Angela Saric (24237573)
 */
public class StudentModifyMajor implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;
    private ArrayList<String> listOfMajors;

    public StudentModifyMajor(Student currentStudent) {
        this.currentStudent = currentStudent;
        this.listOfMajors = new ArrayList<String>();
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

    public void getAvailableMajors() {
        this.listOfMajors.clear();
        for (String major : FilesManager.allMajors) {
            if (!currentStudent.getMajor().equals(major)) {
                listOfMajors.add(major);
            }
        }
    }

    @Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.getAvailableMajors();
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine().trim();

            if (NavigationUtil.backOrExit(userInput)) {
                return "b";
            }

            while (!ValidationUtil.checkIntegerRange(userInput, 1, this.listOfMajors.size())) {
                HeadersUtil.printHeader("Invalid input, please pick a valid option.");
                this.showMenu();
                userInput = scan.nextLine();
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }
            }

            int validOption = Integer.parseInt(userInput);
            String chosenMajor = this.listOfMajors.get(validOption - 1);

            HeadersUtil.printHeader("Changing your major will withdraw you",
                    "from all your current courses.",
                    "Your grades will appear as 'Withdrawn' and",
                    "won't count towards your GPA.",
                    "Are you sure you want to proceed?");
            System.out.println("y - Yes, Change My Major\nb - Go Back (Available Majors)\nx - Exit");
            userInput = scan.nextLine().trim();

            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    validInput = true;
                } else if (userInput.equalsIgnoreCase("y")) {

                    currentStudent.setMajor(chosenMajor);

                    FilesManager.withdrawAllCourses(currentStudent);
                    FilesManager.saveCurrentStudent(currentStudent);

                    HeadersUtil.printHeader("Your major was successfully updated to: ",
                            chosenMajor + "!",
                            "To enroll into your new courses enter in 'b'",
                            "and select '3 - Change Courses'.");
                    validInput = true;
                } else {
                    HeadersUtil.printHeader("Invalid input, please pick one of the following:");
                    System.out.println("y - Yes, Change My Major\nb - Go Back (Available Majors)\nx - Exit");
                    userInput = scan.nextLine().trim();
                }
            }
        }
    }
}