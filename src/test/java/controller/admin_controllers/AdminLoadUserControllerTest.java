/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller.admin_controllers;

import dao.AdminDAO;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import model.DatabaseManager;
import objects.Admin;
import objects.Lecturer;
import objects.Student;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utility_classes.NavigationUtil;
import utility_classes.ValidationUtil;
import view.admin_view.AdminLoadUserView;
import utility_classes.PopUpUtil;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Test cases for the main functionalities of Admin.
 *
 */
public class AdminLoadUserControllerTest {

    private Admin admin;

    public AdminLoadUserControllerTest() {}

    @BeforeClass
    public static void setUpClass() {
        //Initialize DB + connection for DAO methods
        DatabaseManager databaseManager = new DatabaseManager();

        //disable visible GUI
        NavigationUtil.testMode = true;
        //disable pop ups
        PopUpUtil.testMode = true;
    }

    @AfterClass
    public static void tearDownClass() {
        //close connection to DB when tests are done
        //+ set the testmodes off for GUI and pop-ups
        DatabaseManager.closeConnection();
        NavigationUtil.testMode = false;
        PopUpUtil.testMode = false;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //testing loading of a valid student
    @Test
    public void adminLoadStudentTest() {
        //ID of valid student
        String testUserId = "24229618";

        //create a test frame
        AdminLoadUserView testFrame = new AdminLoadUserView(admin) {
            @Override
            public String getUserIdField() {
                return testUserId;
            }
        };

        //initilise controller
        AdminLoadUserController controller = new AdminLoadUserController(testFrame, admin);

        //option 1: search up user 
        controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "1"));

        //assert if student exsists
        assertNotNull("Student should exist", new StudentDAO().getById(Integer.parseInt(testUserId)));
    }

    //testing of loading a valid lecturer
    @Test
    public void adminLoadLecturerTest() {
        //ID of valid lecturer
        String testUserId = "14568912";

        //create a test frame
        AdminLoadUserView testFrame = new AdminLoadUserView(admin) {
            @Override
            public String getUserIdField() {
                return testUserId;
            }
        };

        //initilise controller
        AdminLoadUserController controller = new AdminLoadUserController(testFrame, admin);

        //option 1: search user 
        controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "1"));

        //assert if lecturer exsists
        assertNotNull("Lecturer should exist", new LecturerDAO().getById(Integer.parseInt(testUserId)));
    }

    //testing loading of an invalid user
    @Test
    public void adminLoadInvalidUserTest() {
        //invalid user ID
        String testUserId = "99999999";

        //create a test frame
        AdminLoadUserView testFrame = new AdminLoadUserView(admin) {
            @Override
            public String getUserIdField() {
                return testUserId;
            }
        };

        //initilise controller
        AdminLoadUserController controller = new AdminLoadUserController(testFrame, admin);

        //option 1: search user 
        controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "1"));

        //assert if lecturer exists
        assertNull("User should NOT exist in Student table", new StudentDAO().getById(Integer.parseInt(testUserId)));
        assertNull("User should NOT exist in Lecturer table", new LecturerDAO().getById(Integer.parseInt(testUserId)));
        assertNull("User should NOT exist in Admin table", new AdminDAO().getById(Integer.parseInt(testUserId)));
    }

    //testing of making an update to a valid student's password
    @Test
    public void updateStudentPasswordTest() {
        String userId = "24229618"; //valid student ID
        String newPassword = "NewTestPass123"; //new password

        //initialise studentDAO and student object 
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getById(Integer.parseInt(userId));
        String oldPassword = student.getPassword();

        //assert if user is loaded
        assertNotNull("Student should be loaded", student);

        //set students new password
        student.setPassword(newPassword);
        student.saveCurrrentUser(); //save changes to DB

        //Re-load student from DB
        Student updatedStudent = studentDAO.getById(Integer.parseInt(userId));

        //assert that the password has been updated
        assertEquals("Password should be updated", newPassword, updatedStudent.getPassword());

        //set password back to what it was originally
        student.setPassword(oldPassword);
        student.saveCurrrentUser();
    }

    //testing of making an update to a valid student's password
    @Test
    public void updateLecturerPasswordTest() {
        String userId = "14568912"; //valid lecturer ID
        String newPassword = "NewTestPass123"; //new password

        //initilise lecturerDAO and lecturer object
        LecturerDAO lecturerDAO = new LecturerDAO();
        Lecturer lecturer = lecturerDAO.getById(Integer.parseInt(userId));
        String oldPassword = lecturer.getPassword();

        //assert if user is loaded
        assertNotNull("Lecturer should be loaded", lecturer);

        //set lecturers new password
        lecturer.setPassword(newPassword);
        lecturer.saveCurrrentUser(); //save changes to DB

        //Re-load lecturer from DB
        Lecturer updatedLecturer = lecturerDAO.getById(Integer.parseInt(userId));

        //assert password had been updated
        assertEquals("Password should be updated", newPassword, updatedLecturer.getPassword());

        //set password back to what it was originally
        lecturer.setPassword(oldPassword);
        lecturer.saveCurrrentUser();
    }

    //testing admin registering a new student
    @Test
    public void adminRegisterNewStudentTest() {
        StudentDAO studentDAO = new StudentDAO(); //initilize DAO
        Student newStudent = new Student(); //make new student with default constructor

        //call the method for generating a new ID for new users
        int generatedId = newStudent.generateNewUserId();

        //set values for newStudent 
        String password = "TestPass123";
        String firstName = "Tester";
        String lastName = "Tester";
        String dob = "2005-09-15";
        String email = "testingNewUser@gmail.com";
        String phone = "0222222222";
        String formattedPhone = ValidationUtil.formatPhoneNumber(phone);
        String address = "123 Test Street Suburb City 1234";
        String major = "Computer Science";
        char gender = 'M';
        String uniEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@aut.ac.nz";

        //create new student object with ID and information set
        newStudent = new Student(
                generatedId, password, firstName, lastName, dob, email,
                uniEmail, formattedPhone, gender, address, major
        );

        //inserts new user into the DB
        studentDAO.createNewUser(newStudent);

        //load new user from DB 
        Student savedStudent = studentDAO.getById(generatedId);
        assertNotNull("Student should be saved and retrieved", savedStudent);

        //assert name and major
        assertEquals("Names should match", firstName, savedStudent.getFirstName());
        assertEquals("Major should match", major, savedStudent.getMajor());

        //remove the test user from the database
        newStudent.removeCurrentUser();
    }

    //testing admin registering a new lecturer
    @Test
    public void adminRegisterNewLecturerTest() {
        LecturerDAO lecturerDAO = new LecturerDAO(); //initilize DAO
        Lecturer newLecturer = new Lecturer(); //make new lecturer with default constructor

        //call the method for generating a new ID for new users
        int generatedId = newLecturer.generateNewUserId();

        //set values for newLecturer 
        String password = "PassforTests348";
        String firstName = "Test";
        String lastName = "Tester";
        String dob = "1978-11-28";
        String email = "somethinnew@yahoo.com";
        String phone = "+64 98 567 7723";
        String formattedPhone = ValidationUtil.formatPhoneNumber(phone);
        String address = "987 Somewhere Road Nook Town 7654";
        String faculty = "Data Science";
        char gender = 'F';
        String uniEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@aut.ac.nz";

        //create new lecturer object with ID and information set
        newLecturer = new Lecturer(
                generatedId, password, firstName, lastName, dob, email,
                uniEmail, formattedPhone, gender, address, faculty
        );

        //inserts new user into the DB
        lecturerDAO.createNewUser(newLecturer);

        //load new user from DB 
        Lecturer savedLecturer = lecturerDAO.getById(generatedId);
        assertNotNull("Lecturer should be saved and retrieved", savedLecturer);

        //assert name and major
        assertEquals("Names should match", firstName, savedLecturer.getFirstName());
        assertEquals("Faculty should match", faculty, savedLecturer.getFaculty());

        //remove the test user from the database
        newLecturer.removeCurrentUser();
    }
}
