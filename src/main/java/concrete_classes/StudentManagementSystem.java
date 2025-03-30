/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes;

import java.util.Scanner;
import utility_classes.DashboardUtil;

/**
 *
 * @author Angela Saric (24237573)
 * 
 * This starts up the program; main method.
 */
public class StudentManagementSystem {
    
    public static void main(String[] args) {
        
        System.out.println("====================================");
        System.out.println("     Student Management System      ");
        System.out.println("====================================");
        
        System.out.println("1. Login as Student\n2. Login as Lecturer\n3. Login as Administrator\nx Exit");
        System.out.println("====================================");
        
        Scanner scan = new Scanner(System.in);

        //input validation called here
        String option = scan.nextLine();
        
        //AuthenticatorManager.login();
        //within the authenticator, ask the user to type in username and password
        //depending on the option they chose, read from students.txt, lecturers.txt OR admins.txt
        //if the person exists but they get their password wrong/if the person doesnt exist ==> try again
        //within this they can always quit or return
        //return appropriate object
        
        DashboardUtil.showMenu(option);
    }
}