/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.run;

import concrete_classes.authentication.UserAuthentication;
import concrete_classes.other.DashboardUtil;
import concrete_classes.other.HeadersUtil;
import concrete_classes.authentication.LoginOptionValidator;
import concrete_classes.other.NavigationUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;

/**
 *
 * @author Angela Saric (24237573)
 *
 * Dashboard which is executed first when the program is run.
 */
public class MainDashboard implements DashboardInterface, HeaderInterface {

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Welcome to the Student Management System!", "Please pick an option:");
    }

    @Override
    public void showMenu() {
        System.out.println("1 - Login as Student\n2 - Login as Lecturer\n3 - Login as Administrator\nx - Exit");
    }

    public void run() {

        while (true) {
            /* shows the main dashboard header and menu options */
            this.showHeader();
            this.showMenu();

            /*
            validates main dashboard user input
            - if user input is invalid ==> prompted again
            - if input is valid ==> return the user type and load up the users hashmap
                relative to the chosen user type
            - user can choose to exit anytime
             */
            LoginOptionValidator loginOption = new LoginOptionValidator();
            String userInput = loginOption.validateUserInput(); //"1", "2", etc. . .

            /*
            authenticates user by asking them for a username and password, and
            prompts user with helpful messages if invalid input is given
            - user can choose to exit or go back to the main dashboard screen
             */
            UserAuthentication userAuthentication = new UserAuthentication();
            if (!userAuthentication.login()) { //if user wants to go back, restart the loop
                continue;
            }

            /*
            based on user choice, call the appropriate dashboard and display their options. . .
            (this is where the logic gets separated depending on the chosen dashboards)
             */
            String finalUserInput = DashboardUtil.displayDashboards(userInput);
            NavigationUtil.checkExit(finalUserInput);

            /*
            - should we store students in a hashset instead?
                - no for now
            
            If we feel like it later:
            - should students be able to change names?
            - should we implement the "confirm your password" feature?
            
            
            WILL
            - should lecturers be able to tell students "they have finished a course, sign off grade"?
            
            notes after tidy up, quick things to improve:
            - is it better to load up all files right at the beginning of the program
            - lecturer extension not being used or shown anywhere, faculty not being shown anywhere
            - in all users uniEmail and DOB not really being utilised
                - could admin somehow use this info? or could this info be used in some way
                  anywhere else in the program? even if just being displayed and reformatted
                  e.g. what i did with gender for example
            - lecturer edit course should have restrictions ==> ValidationUtil methods
            - linked or not linked for the static variables?
            
            GIT:
            - commit changes to local fake main
            - check out CUI
            - commit the final changes to fake main after discussion
            
            - push my local fake main to remote fake main (after checking out CUI)
            - merge CUI branch to fake main (Will)
                - resolve conflicts
            
            - do final little changes in fake main (^ Will)
            - commit fake main to main
            
            - work on admin
            
            OTHER:
            - generate .txt files
            - comments
            
            */
        }
    }
}