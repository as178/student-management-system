/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.components;

import abstract_classes.User;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573)
 */
public class Student extends User {

    private String major;
    private HashMap<String, Float> enrolledCourses;
    private HashMap<String, Float> completedCourses;

    public Student(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address, String major) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
        this.major = major;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }
}