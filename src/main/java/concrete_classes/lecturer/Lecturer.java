package concrete_classes.lecturer;

import abstract_classes.User;
import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author williamniven
 */
public class Lecturer extends User {

    protected String faculty;
    protected HashMap<Integer, Course> coursesTaught;

    public Lecturer(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address, String faculty) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
        this.faculty = faculty;
        this.coursesTaught = new HashMap<Integer, Course>();
    }

    public String getFaculty() {
        return this.faculty;
    }

    public HashMap<Integer, Course> getCoursesTaught() {
        return this.coursesTaught;
    }

    @Override
    public String toString() {
        return id + "," + password + "," + firstName + "," + lastName + ","
                + dateOfBirth + "," + personalEmail + "," + uniEmail + ","
                + phoneNumber + "," + gender + "," + address + "," + faculty;
    }

    @Override
    public String getUsersPath() {
        return FilesManager.allLecturersFile;
    }
}