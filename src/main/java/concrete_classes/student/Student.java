/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import abstract_classes.User;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.GradesUtil;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573)
 */
public class Student extends User {

    protected String major;
    protected HashMap<String, Float> enrolledCourses;
    protected HashMap<String, Float> previousCourses;

    public Student(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address, String major) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
        this.major = major;
        this.enrolledCourses = new HashMap<String, Float>();
        this.previousCourses = new HashMap<String, Float>();
    }

    public String getMajor() {
        return this.major;
    }

    public float getGPA() {
        float gradePoints = 0;
        int numberOfCourses = 0;

        for (Float grade : enrolledCourses.values()) {
            if (grade != null && grade != -1f) {
                gradePoints += GradesUtil.convertFloatToGPA(grade);
                numberOfCourses++;
            }
        }

        for (Float grade : previousCourses.values()) {
            if (grade != null && grade != -1f) {
                gradePoints += GradesUtil.convertFloatToGPA(grade);
                numberOfCourses++;
            }
        }

        if (numberOfCourses == 0) {
            return 0;
        }

        return gradePoints / numberOfCourses;
    }

    public HashMap<String, Float> getEnrolledCourses() {
        return this.enrolledCourses;
    }

    public HashMap<String, Float> getPreviousCourses() {
        return this.previousCourses;
    }
    
    public void setMajor(String newMajor){
        this.major = newMajor;
    }

    @Override
    public String toString() {
        return id + "," + password + "," + firstName + "," + lastName + ","
                + dateOfBirth + "," + personalEmail + "," + uniEmail + ","
                + phoneNumber + "," + gender + "," + address + "," + major;
    }
    
    @Override
    public String getUsersPath() {
        return FilesManager.allStudentsFile;
    }
}