/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes;

import concrete_classes.components.Student;
import concrete_classes.file_input_output.FilesManager;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import other_classes.HeadersUtil;

/**
 *
 * @author Angela Saric (24237573)
 *
 * This is the dashboard that shows students their options upon logging in.
 */
public class StudentDashboard implements DashboardInterface, HeaderInterface{

    @Override
    public void showHeader() {
        Student currentStudent = (Student) FilesManager.currentUser;
        HeadersUtil.printHeader("Welcome to the Student Dashboard,",
                currentStudent.getFirstName() + " " + currentStudent.getLastName() + "!", "What would you like to do?");
    }
    
    @Override
    public void showMenu(){
        System.out.println("1 - View My Details\n2 - Currently Enrolled Courses\n3 - View My Grades\nx - Exit");
    }
}
