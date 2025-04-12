/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

import concrete_classes.file_input_output.FilesManager;

/**
 *
 * @author williamniven
 */
public class StudentViewDetails {
    
    public static void viewMyDetails() {
        Student currentStudent = (Student) FilesManager.currentUser;
        System.out.println("\n==== My Details ====\n");
        System.out.println("ID: " + currentStudent.getId());
        System.out.println("Name: " + currentStudent.getFirstName() + " " + currentStudent.getLastName());
        System.out.println("Date of Birth: " + currentStudent.getDateOfBirth());
        System.out.println("Personal Email: " + currentStudent.getPersonalEmail());
        System.out.println("University Email: " + currentStudent.getUniEmail());
        System.out.println("Phone Number: " + currentStudent.getPhoneNumber());
        System.out.println("Gender: " + currentStudent.getGender());
        System.out.println("Address: " + currentStudent.getAddress());
        System.out.println("Major: " + currentStudent.getMajor());
        System.out.println("\n====================\n");
        System.out.println("1 - Edit My Details\n2 - Back\nx - Exit");
    }

    public static void veiwEditDetails() {
        Student currentStudent = (Student) FilesManager.currentUser;
        System.out.println("\n==== Edit Details ====\n");
        System.out.println("1) Personal Email: " + currentStudent.getPersonalEmail());
        System.out.println("2) Phone Number: " + currentStudent.getPhoneNumber());
        System.out.println("3) Address: " + currentStudent.getAddress());
        System.out.println("\n======================\n");
    }

    public static void currentlyEnrolledCourses() {
        Student currentStudent = (Student) FilesManager.currentUser;
        System.out.println("Enrolled Courses: " + currentStudent.getEnrolledCourses());
    }
}