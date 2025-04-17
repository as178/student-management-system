/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.Scanner;

/**
 *
 * @author williamniven
 */
public class LecturerVeiwDetails implements DashboardInterface, HeaderInterface, InputValidationInterface {
    
    private Lecturer currentLecturer;
    
    public LecturerVeiwDetails(Lecturer currentLecturer){
        this.currentLecturer = currentLecturer;
    }
    
    @Override
    public void showHeader() {
        HeadersUtil.printHeader("My Details");
    }
    
    @Override
    public void showMenu() {
        System.out.println("ID: " + currentLecturer.getId());
        System.out.println("Name: " + currentLecturer.getFirstName() + " " + currentLecturer.getLastName());
        System.out.println("Date of Birth: " + currentLecturer.getDateOfBirth());
        System.out.println("Personal Email: " + currentLecturer.getPersonalEmail());
        System.out.println("University Email: " + currentLecturer.getUniEmail());
        System.out.println("Phone Number: " + currentLecturer.getPhoneNumber());
        System.out.println("Gender: " + currentLecturer.getGender());
        System.out.println("Address: " + currentLecturer.getAddress());
        HeadersUtil.printHeader("Please choose one of the options below:");
        System.out.println("1 - Modify Your Details\nb - Go Back (Lecturer Dashboard)\nx - Exit");
    }
    
@Override
    public String validateUserInput() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine().trim();

            boolean validInput = false;
            while (!validInput) {
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                switch (userInput) {
                    case "1":
                        LecturerModifyDetails modifyDetails = new LecturerModifyDetails(currentLecturer);
                        modifyDetails.validateUserInput();
                        validInput = true;
                        break;
                    default:
                        HeadersUtil.printHeader("Invalid input, please see below", "for valid options.");
                        this.showMenu();
                        userInput = scan.nextLine();
                }
            }
        }
    }
}
