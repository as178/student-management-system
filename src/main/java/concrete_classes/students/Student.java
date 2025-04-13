/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

import abstract_classes.User;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573)
 */
public class Student extends User {

    private String major;
    //private int gpa;
    private HashMap<String, Float> enrolledCourses;
    private HashMap<String, Float> completedCourses;

    public Student(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address, String major) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
        this.major = major;
        this.enrolledCourses = new HashMap<>();
        this.completedCourses = new HashMap<>();
    }

    public String getMajor() {
        return this.major;
    }
    
//    public int getGPA(){
//        to be implemented after courses section for student
//    }

    public HashMap<String, Float> getEnrolledCourses() {
        return this.enrolledCourses;
    }

    public HashMap<String, Float> getCompletedCourses() {
        return this.completedCourses;
    }

    @Override
    public String toString() {
        return id + "," + password + "," + firstName + "," + lastName + ","
                + dateOfBirth + "," + personalEmail + "," + uniEmail + ","
                + phoneNumber + "," + gender + "," + address + "," + major;
    }
}
