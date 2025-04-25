/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

import concrete_classes.admin.AdminDashboard;
import concrete_classes.lecturer.LecturerDashboard;
import concrete_classes.student.StudentDashboard;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 * 
 * Depending on the authentication result of the user
 * logging in, this utility class will call the appropriate
 * dashboards to display to the user. It will otherwise return "b"
 * to go back.
 */
public final class DashboardUtil {
   
    private DashboardUtil() {}
    
    public static String displayDashboards(String userInput){
        switch(userInput){
            case "1":
                StudentDashboard studentDashboard = new StudentDashboard();
                return studentDashboard.validateUserInput();
            case "2":
                LecturerDashboard lecturerDashboard = new LecturerDashboard();
                return lecturerDashboard.validateUserInput();
            case "3":
                AdminDashboard adminDashboard = new AdminDashboard();
                return adminDashboard.validateUserInput();
            default:
                return "b";
        }
    }
}
