/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.run;

import concrete_classes.MainDashboard;
import concrete_classes.components.Student;
import concrete_classes.file_input_output.FilesManager;
import java.util.Scanner;
import other_classes.LoginUserInputValidation;

/**
 *
 * @author Angela Saric (24237573)
 *
 * Code which is executed first when the program is run.
 */
public class StudentManagementSystemRun {

    public static MainDashboard mainDashboard = new MainDashboard();

    public void run() {
        mainDashboard.showHeader();
        mainDashboard.showMenu();

        Scanner scan = new Scanner(System.in);
        String userInput = new String();

        LoginUserInputValidation loginUserValidation = new LoginUserInputValidation();
        loginUserValidation.validateUserInput(userInput, scan); 
        
        //checks if currentuser is student object then runs the student RUN method
        if (FilesManager.currentUser instanceof Student) {
            StudentRun SR = new StudentRun();
            SR.StudentRun(userInput, scan);
        }

        /*
        to implement now:
        - the "go back" option
        - the logout option
        these options should always be available (?), as well as the shutdown option
        
        next to implement:
        - writing method for students
        - making an interface for authentication, login() - not necessary
         */
    }
}
