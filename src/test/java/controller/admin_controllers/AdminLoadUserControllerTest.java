/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller.admin_controllers;

import abstract_classes.User;
import dao.AdminDAO;
import dao.CourseDAO;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Random;
import model.DatabaseManager;
import model.DatabaseTablesCreation;
import objects.Admin;
import objects.Lecturer;
import objects.Student;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utility_classes.ValidationUtil;
import view.admin_view.AdminLoadUserView;
import view.admin_view.AdminRegisterNewUserView;

/**
 *
 * @author williamniven
 */
public class AdminLoadUserControllerTest {

    private Admin admin;

    public AdminLoadUserControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        //Initialize DB connection for DAO methods
        DatabaseManager databaseManager = new DatabaseManager();
        
        //disable visible GUI
        utility_classes.NavigationUtil.testMode = true;
        utility_classes.PopUpUtil.testMode = true; //disable pop ups
    }

    @AfterClass
    public static void tearDownClass() {
        DatabaseManager.closeConnection();
    }

    @Before
    public void setUp() {  
        //before each test, drop all tables so as to reset the data + prevent duplicates via testing
        //recreate the tables anew after dropping (which will populate the tables with default data)
        DatabaseTablesCreation tablesCreation = new DatabaseTablesCreation(DatabaseManager.getCurrentConnection());
        tablesCreation.dropAllTables();
        tablesCreation.createTables();
        
        //load admin
        AdminDAO adminDAO = new AdminDAO();
        HashMap<String, User> allUsers = adminDAO.getAllUsers();
        admin = (Admin) allUsers.get("10567001");

        //check if test admin has loaded
        assertNotNull("Test lecturer not found in DAO", admin);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdminLoadStudent() {
        //ID of valid student
        String testUserId = "24229618";

        //create a test frame
        AdminLoadUserView testFrame = new AdminLoadUserView(admin) {
            @Override
            public String getUserIdField() {
                return testUserId;
            }
        };
        utility_classes.NavigationUtil.initialFrame(testFrame);

        //initilise controller
        AdminLoadUserController controller = new AdminLoadUserController(testFrame, admin);

        //option 1: serch user 
        controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "1"));

        //assert if student exsists
        StudentDAO studentDAO = new StudentDAO();
        assertTrue("Student should exist", studentDAO.getAllUsers().containsKey(testUserId));
    }

    @Test
    public void testAdminLoadLecturer() {
        //ID of valid lecturer
        String testUserId = "14568912";

        //create a test frame
        AdminLoadUserView testFrame = new AdminLoadUserView(admin) {
            @Override
            public String getUserIdField() {
                return testUserId;
            }
        };
        utility_classes.NavigationUtil.initialFrame(testFrame);

        //initilise controller
        AdminLoadUserController controller = new AdminLoadUserController(testFrame, admin);

        //option 1: serch user 
        controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "1"));

        //assert if lecturer exsists
        LecturerDAO lecturerDAO = new LecturerDAO();
        assertTrue("Lecturer should exist", lecturerDAO.getAllUsers().containsKey(testUserId));
    }

    @Test
    public void testAdminLoadInvalidUser() {
        //invalid user ID
        String testUserId = "99999999";

        //create a test frame
        AdminLoadUserView testFrame = new AdminLoadUserView(admin) {
            @Override
            public String getUserIdField() {
                return testUserId;
            }
        };
        utility_classes.NavigationUtil.initialFrame(testFrame);

        //initilise controller
        AdminLoadUserController controller = new AdminLoadUserController(testFrame, admin);

        //option 1: serch user 
        controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "1"));

        //assert if lecturer exsists
        LecturerDAO lecturerDAO = new LecturerDAO();
        assertFalse("User should NOT exist", lecturerDAO.getAllUsers().containsKey(testUserId));
    }

    @Test
    public void testUpdateStudentPassword() {
        String userId = "24229618"; //valid student ID
        String newPassword = "NewTestPass123"; //new password

        //initilise studentDAO and student object 
        StudentDAO studentDAO = new StudentDAO();
        Student student = (Student) studentDAO.getAllUsers().get(userId);

        //assert if user is loaded
        assertNotNull("Student should be loaded", student);

        //set students new password
        student.setPassword(newPassword);
        student.saveCurrrentUser(); //save changes to DB

        //Re-load student from DB
        Student updatedStudent = (Student) studentDAO.getAllUsers().get(userId);

        //assert that the password has been updated
        assertEquals("Password should be updated", newPassword, updatedStudent.getPassword());
    }

    @Test
    public void testUpdateLecturerPassword() {
        String userId = "14568912"; //valid lecturer ID
        String newPassword = "NewTestPass123"; //new password

        //initilise lecturerDAO and lecturer object
        LecturerDAO lecturerDAO = new LecturerDAO();
        Lecturer lecturer = (Lecturer) lecturerDAO.getAllUsers().get(userId);

        //assert if user is loaded
        assertNotNull("Lecturer should be loaded", lecturer);

        //set lecturers new password
        lecturer.setPassword(newPassword);
        lecturer.saveCurrrentUser(); // Persist to DB

        //Re-load lecturer from DB
        Lecturer updatedLecturer = (Lecturer) lecturerDAO.getAllUsers().get(userId);

        //assert password had been updated
        assertEquals("Password should be updated", newPassword, updatedLecturer.getPassword());
    }

    @Test
    public void testAdminRegisterNewStudent() {
        StudentDAO studentDAO = new StudentDAO(); //initilize DAO
        Random rand = new Random(); //initilize random object
        Student newStudent = new Student(); //make new student with default constructor
        
        //initialise view which has the method for generating a new ID for new users
        AdminRegisterNewUserView regUser = new AdminRegisterNewUserView(newStudent);
        int generatedId = regUser.generateNewUserID();        

        //set values for new Student 
        String password = "TestPass123";
        String firstName = "jhvjhgb";
        String lastName = "kjhnbkj";
        String dob = "2005-09-15";
        String email = "somethingnew@gmail.com";
        String phone = "0222222222";
        String formattedPhone = utility_classes.ValidationUtil.formatPhoneNumber(phone);
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
    }

    @Test
    public void testAdminRegisterNewLecturer() {
        LecturerDAO lecturerDAO = new LecturerDAO(); //initilize DAO
        Random rand = new Random(); //initilize random
        Lecturer newLecturer = new Lecturer();
        
        //initialise view which has the method for generating a new ID for new users
        AdminRegisterNewUserView regUser = new AdminRegisterNewUserView(newLecturer);
        int generatedId = regUser.generateNewUserID();

        //set values for new Lecturer 
        String password = "TestPass123";
        String firstName = "ksjdbvef";
        String lastName = "sljegnkjesng";
        String dob = "2005-09-15";
        String email = "sdkrgjnv.srbgkjhns@skdjgrn.com";
        String phone = "+67890005555";
        String formattedPhone = ValidationUtil.formatPhoneNumber(phone);
        String address = "123 Test Street Suburb City 1234";
        String faculty = "Computer Science";
        char gender = 'M';
        String uniEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@aut.ac.nz";
        
        //create new student object with ID and information set
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
        assertEquals("Major should match", faculty, savedLecturer.getFaculty());
    }
}
