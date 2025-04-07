/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.run;

import concrete_classes.StudentDashboard;
import java.util.Scanner;
import other_classes.MainStudentDashboardInputValidation;

/**
 *
 * @author williamniven
 */
public class StudentRun {

    public void StudentRun(String userInput, Scanner scan) {
        StudentDashboard S = new StudentDashboard();
        MainStudentDashboardInputValidation MDIV = new MainStudentDashboardInputValidation();
        
        //shows menu
        S.showHeader();
        S.showMenu();
        //take input
        userInput = scan.nextLine();
        MDIV.validateUserInput(userInput, scan);
        
    }
}
