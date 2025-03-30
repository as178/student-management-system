/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes;

import interfaces.DashboardInterface;

/**
 *
 * @author Angela Saric (24237573)
 * 
 * This is the dashboard that shows students their options upon logging in.
 */
public class StudentDashboard implements DashboardInterface {

    @Override
    public void showMenu() {
        System.out.println("Ello");
        System.out.println("Login options here:");
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
    }
    
}
