/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other_classes;

import concrete_classes.StudentDashboard;

/**
 *
 * @author Angela Saric (24237573)
 * 
 * Depending on the authentication result of the user
 * logging in, this utility class will call the appropriate
 * dashboards to display to the user.
 */
public final class DashboardUtil {
   
    private DashboardUtil(){}
    
    public static void showMenu(String option){
        switch(option){
            case "1":
                new StudentDashboard().showMenu();
                break;
        }
    }
}
