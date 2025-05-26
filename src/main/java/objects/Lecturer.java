/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import abstract_classes.User;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The Lecturer class extends User and provides basic lecturer
 * attributes and methods to retrieve them.
 * 
 */
public class Lecturer extends User {

    protected String faculty;
    protected HashMap<String, Course> coursesTaught;

    public Lecturer(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address, String faculty) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
        this.faculty = faculty;
        this.coursesTaught = new HashMap<String, Course>();
    }

    public String getFaculty() {
        return this.faculty;
    }

    public HashMap<String, Course> getCoursesTaught() {
        return this.coursesTaught;
    }
}
