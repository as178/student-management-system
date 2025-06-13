/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import abstract_classes.User;
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
import view.LoginView;
import utility_classes.PopUpUtil;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Test cases for the initial login logic of the program.
 *
 */
public class LoginControllerTest {

    //login options for the controller
    private static final String STUDENT = "1";
    private static final String LECTURER = "2";
    private static final String ADMIN = "3";

    public LoginControllerTest() {}

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
        UserController.logOutCurrentUser();
        UserController.getCurrentUsers().clear();
    }

    /**
     * testing valid login for all user types: Student, Lecturer, Admin
     */
    @Test
    public void studentLoginSuccessTest() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(); //STUDENT = student
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("24229618"); //student ID
        loginView.setPassword("mySchoolPass"); //correct student password

        //Create controller and simulate student login STUDENT option
        LoginController controller = new LoginController(loginView);
        ActionEvent loginEvent = new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT);
        controller.actionPerformed(loginEvent);

        //Assert that login succeeded
        User currentUser = UserController.getCurrentUser();
        assertNotNull("Student login failed", currentUser);
        assertTrue(currentUser instanceof Student);
        assertEquals("24229618", currentUser.getId() + "");
        assertEquals("mySchoolPass", currentUser.getPassword() + "");
    }

    @Test
    public void lecturerLoginSuccessTest() {
        //Set current users to only lecturers for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(); //LECTURER = lecturer
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("14568912"); //lecturer ID
        loginView.setPassword("BrightTiger7"); //correct lecturer password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert that login succeeded
        User currentUser = UserController.getCurrentUser();
        assertNotNull("Lecturer login failed", currentUser);
        assertTrue(currentUser instanceof Lecturer);
        assertEquals("14568912", currentUser.getId() + "");
        assertEquals("BrightTiger7", currentUser.getPassword() + "");
    }

    @Test
    public void adminLoginSucessTest() {
        //Set current users to only admins for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(); //ADMIN = admin
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("10567001"); //admin ID
        loginView.setPassword("adminpassword123"); //correct admin password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert that login succeeded
        User currentUser = UserController.getCurrentUser();
        assertNotNull("Admin login failed", currentUser);
        assertTrue(currentUser instanceof Admin);
        assertEquals("10567001", currentUser.getId() + "");
        assertEquals("adminpassword123", currentUser.getPassword() + "");
    }

    /**
     * testing Invalid ID for all user types: Student, Lecturer & Admin, with
     * valid password
     */
    @Test
    public void studentLoginWrongIdTest() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(); //STUDENT = student
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("99999999"); //wrong ID
        loginView.setPassword("mySchoolPass"); //correct student password

        //Create controller and simulate student login STUDENT option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void lecturerLoginWrongIdTest() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(); //LECTURER = lecturer
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("99999999"); //wrong ID
        loginView.setPassword("BrightTiger7"); //correct lecturer password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void adminLoginWrongIdTest() {
        //Set current users to only admins for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(); //ADMIN = admin
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("99999999"); //wrong ID
        loginView.setPassword("adminpassword123"); //correct admin password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    /**
     * testing valid ID for all user types: Student, Lecturer & Admin with
     * incorrect password
     */
    @Test
    public void studentLoginWrongPasswordTest() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(); //STUDENT = student
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("24229618"); //student ID
        loginView.setPassword("wrongpass"); // wrong password

        //Create controller and simulate student login STUDENT option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void lecturerLoginWrongPasswordTest() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(); //LECTURER = lecturer
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("14568912"); //lecturer ID
        loginView.setPassword("wrongpass"); // wrong password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void adminLoginWrongPasswordTest() {
        //Set current users to only admins for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(); //ADMIN = admin
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername("10567001"); //admin ID
        loginView.setPassword("wrongpass"); // wrong password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    /**
     * testing empty ID with valid password for all user types
     */
    @Test
    public void studentLoginEmptyIdTest() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(); //STUDENT = student
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword("mySchoolPass"); //correct student password

        //Create controller and simulate student login STUDENT option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void lecturerLoginEmptyIdTest() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(); //LECTURER = lecturer
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword("BrightTiger7"); //correct lecturer password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void adminLoginEmptyIdTest() {
        //Set current users to only admins for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(); //ADMIN = admin
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword("adminpassword123"); //correct admin password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    /**
     * testing empty ID and empty password for all user types
     */
    @Test
    public void studentLoginEmptyTest() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(); //STUDENT = student
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword(""); //Empty password

        //Create controller and simulate student login STUDENT option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void lecturerLoginEmptyTest() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(); //STUDENT = lecturer
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword(""); //Empty password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void adminLoginEmptyTest() {
        //Set current users to only lecturer for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(); //ADMIN = admin
        utility_classes.NavigationUtil.newFrame(loginView); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword(""); //Empty password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }
}
