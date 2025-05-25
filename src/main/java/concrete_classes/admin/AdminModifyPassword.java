/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.admin;

import abstract_classes.User;
import concrete_classes.other.HeadersUtil;
import java.util.Scanner;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class extends the abstract UserModifyDetails class for editing personal
 * details, which is used by every user. In this context, the targetUser is
 * passed into the class and their get & set methods are used for information
 * access/manipulation. This class enables admins to change the 'target' user's
 * password and see their information.
 *
 */
public class AdminModifyPassword {

    public AdminModifyPassword(User targetUser) {
        //super(targetUser);
    }

    public void showHeader() {
        HeadersUtil.printHeader("Modify User's Password",
                "Please see further options below.");
    }

    //show information about target user
//    @Override
//    public void showMenu() {
//        System.out.println("ID: " + currentUser.getId());
//        System.out.println("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
//        System.out.println("Date of Birth: " + currentUser.getDateOfBirth());
//        System.out.println("Personal Email: " + currentUser.getPersonalEmail());
//        System.out.println("University Email: " + currentUser.getUniEmail());
//        System.out.println("Phone Number: " + currentUser.getPhoneNumber());
//        System.out.println("Gender: " + (currentUser.getGender() == 'F' ? "Female" : "Male"));
//        System.out.println("Address: " + currentUser.getAddress());
//        HeadersUtil.printHeader("Please pick one of the following.");
//        System.out.println("1 - Modify Password\nb - Go Back (Load Another User)\nx - Exit");
//    }

    /*
    Within this class we override the majority of the methods within
    the UserModifyDetails class to better suit the context.
    The validateUserInput method calls on the modifyPassword method
    from the superclass.
    
    Otherwise, the primary logic behind the validateUserInput method remains
    the same with an outer/inner loop combination for dashboard
    re-displaying and re-prompting in case of invalid input.
    Again the user is always given the option to go back or exit.
     */
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

        outerLoop:
        while (true) {

            this.showHeader();
            //this.showMenu();
            String userInput = scan.nextLine().trim();

            //inner loop
            boolean validInput = false;
            while (!validInput) {
//                if (NavigationUtil.backOrExit(userInput)) {
//                    return "b";
//                }
                switch (userInput) {
                    case "1": //only valid choice, triggers dashboard to modify user's password
//                        if (super.modifyPassword(scan).equalsIgnoreCase("b")) {
//                            continue outerLoop; //restart the loop if user wishes to go back
//                        }
                        validInput = true; //confirms valid input
                        break;
                    default: //otherwise, re-prompt
                        HeadersUtil.printHeader("Invalid option.");
                        //this.showMenu();
                        userInput = scan.nextLine().trim();
                }
            }
        }
    }
}
