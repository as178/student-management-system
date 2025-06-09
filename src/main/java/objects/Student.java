/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import objects.objects_interfaces.NewUserInterface;
import abstract_classes.User;
import controller.UserController;
import dao.StudentDAO;
import utility_classes.GradesUtil;
import java.util.HashMap;
import java.util.Random;
import utility_classes.NavigationUtil;
import view.student_view.StudentDashboardView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The Student class extends User and provides basic student attributes and
 * methods to retrieve them.
 *
 */
public class Student extends User implements NewUserInterface {

    protected String major;
    protected HashMap<String, Float> enrolledCourses;
    protected HashMap<String, Float> previousCourses;

    /*
    Default constructor to create a blank user;
    used when admin creates a new Student user.
     */
    public Student() {}

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

    /*
    Calculates the student's GPA based on the grades
    they have in their enrolledCourses and previousCourses
    hashmaps.
     */
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

    public void setMajor(String newMajor) {
        this.major = newMajor;
    }

    @Override
    public void saveCurrrentUser() {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.update(this);
        UserController.setCurrentUsers(studentDAO.getAllUsers());
    }

    @Override
    public void removeCurrentUser() {
        new StudentDAO().removeUser(this);
    }

    @Override
    public void userMainDashboard() {
        NavigationUtil.newFrame(new StudentDashboardView(this));
    }

    @Override
    public void addNewUserToDatabase() {
        //add this student to the Student table
        new StudentDAO().createNewUser(this);
    }

    @Override
    public int generateNewUserId() {
        Random rand = new Random();

        //Student ID range BETWEEN 20000000 AND 29999999 as specified in the Student table
        int randomId = 20000000 + rand.nextInt(10000000);

        StudentDAO studentDAO = new StudentDAO();

        //while the id is taken, keep regenerating until a valid one is found
        while (studentDAO.getById(randomId) != null) {
            randomId = 20000000 + rand.nextInt(10000000);
        }
        return randomId; //return when valid id is found
    }
}
