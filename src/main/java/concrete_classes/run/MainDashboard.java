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
 * Code which is executed first when the program is run.
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
            if (!userAuthentication.login()) {
                continue;
            }

            /*
            based on user choice, call the appropriate dashboard and display their options. . .
             */
            String finalUserInput = DashboardUtil.displayDashboards(userInput).toLowerCase();
            NavigationUtil.checkExit(finalUserInput);

            /*
            - should we store students in a hashset instead?
                - no for now
            - should students be able to change names?
                - if we feel like it at the end
            
            Will tasks:
            - adding more detailed "go back" print outs
            - testing, change .txt files, see what the outputs are
            
            Angela tasks:
            - populate .txt files with better data
            - go thru student stuff and tidy up everything, checking for wrapping, logic, etc. . .
             */
        }
    }
}
