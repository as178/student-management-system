/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.run;

import concrete_classes.other.NavigationUtil;
import model.DatabaseManager;
import view.ProgramLaunchView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Main method; runs the program.
 */
public class StudentManagementSystem {

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        ProgramLaunchView programLaunchView = new ProgramLaunchView();
        NavigationUtil.initialFrame(programLaunchView); 
    }
}
