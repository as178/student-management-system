/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes;

import concrete_classes.components.Student;
import concrete_classes.file_input_output.FilesManager;
import interfaces.DashboardInterface;
import other_classes.HeadersUtil;

/**
 *
 * @author Angela Saric (24237573)
 *
 * This is the dashboard that shows students their options upon logging in.
 */
public class StudentDashboard implements DashboardInterface {

    @Override
    public void showMenu() {
        Student currentStudent = (Student) FilesManager.currentUser;
        HeadersUtil.printHeader("Welcome to the Student Dashboard,",
                currentStudent.getFirstName() + " " + currentStudent.getLastName() + "!", "What would you like to do?");
    }
}
