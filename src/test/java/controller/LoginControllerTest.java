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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
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
import view.LoginView;

/**
 *
 * @author williamniven
 */
public class LoginControllerTest {

    private static final String STUDENT = "1";
    private static final String LECTURER = "2";
    private static final String ADMIN = "3";

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        UserController.logOutCurrentUser();
    }

    @Before
    public void setUp() {
        //Initialize DB connection for DAO methods
        DatabaseManager.initialiseConnection();

        //disable visible GUI
        utility_classes.NavigationUtil.testMode = true;
        utility_classes.PopUpUtil.testMode = true; //disable pop ups

        //Setup dummy frame to avoid null pointer during NavigationUtil.newFrame
        JFrame testFrame = new JFrame();
        testFrame.setVisible(false); //disable frame visibility
        utility_classes.NavigationUtil.currentFrame = testFrame;

        //AdminDAO for Admin login
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        //lecturerDAO for Lecturer login
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //studentDAO for Student login
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());
    }

    @After
    public void tearDown() {
        UserController.logOutCurrentUser();
    }

    /**
     * testing valid login for all user types Student Lecturer Admin
     */
    @Test
    public void testStudentLoginSuccess() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        HashMap<String, User> allStudents = studentDAO.getAllUsers();
        UserController.setCurrentUsers(allStudents);

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(STUDENT); //STUDENT = student
        loginView.setVisible(false); //prevent GUI from showing
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
    }

    @Test
    public void testLecturerLoginSuccess() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(LECTURER); //STUDENT = lecturer
        loginView.setVisible(false); //prevent GUI from showing
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
    }

    @Test
    public void testAdminLoginSucess() {
        //Set current users to only lecturer for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(ADMIN); //ADMIN = admin
        loginView.setVisible(false); //prevent GUI from showing
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
    }

    /**
     * testing Invalid ID for all user types Student Lecturer Admin with correct
     * password
     */
    @Test
    public void testStudentLoginWrongId() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(STUDENT); //ADMIN = student
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername("99999999"); //wrong ID
        loginView.setPassword("mySchoolPass"); //correct student password

        //Create controller and simulate student login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testLecturerLoginWrongId() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(LECTURER); //STUDENT = lecturer
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername("99999999"); //wrong ID
        loginView.setPassword("BrightTiger7"); //correct lecturer password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testAdminLoginWrongId() {
        //Set current users to only lecturer for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(ADMIN); //ADMIN = admin
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername("99999999"); //wrong ID
        loginView.setPassword("adminpassword123"); //correct admin password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    /**
     * testing Invalid ID for all user types Student Lecturer Admin with correct
     * password
     */
    @Test
    public void testStudentLoginWrongPassword() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(STUDENT); //ADMIN = student
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername("24229618"); //student ID
        loginView.setPassword("wrongpass"); // wrong password

        //Create controller and simulate student login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testLecturerLoginWrongPassword() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(LECTURER); //STUDENT = lecturer
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername("14568912"); //lecturer ID
        loginView.setPassword("wrongpass"); // wrong password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testAdminLoginWrongPassword() {
        //Set current users to only lecturer for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(ADMIN); //ADMIN = admin
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername("10567001"); //admin ID
        loginView.setPassword("wrongpass"); // wrong password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    /**
     * testing empty id with valid password for all user types
     */
    @Test
    public void testStudentLoginEmptyId() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(STUDENT); //ADMIN = student
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword("mySchoolPass"); //correct student password

        //Create controller and simulate student login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testLecturerLoginEmptyId() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(LECTURER); //STUDENT = lecturer
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword("BrightTiger7"); //correct lecturer password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testAdminLoginEmptyId() {
        //Set current users to only lecturer for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(ADMIN); //ADMIN = admin
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword("adminpassword123"); //correct admin password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    /**
     * testing empty id and empty password for all user types
     */
    @Test
    public void testStudentLoginEmpty() {
        //Set current users to only students for this test
        StudentDAO studentDAO = new StudentDAO();
        UserController.setCurrentUsers(studentDAO.getAllUsers());

        LoginView loginView = new LoginView(STUDENT); //ADMIN = student
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword(""); //Empty password

        //Create controller and simulate student login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, STUDENT));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testLecturerLoginEmpty() {
        //Set current users to only lecturer for this test
        LecturerDAO lecturerDAO = new LecturerDAO();
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        //Create LoginView and set credentials
        LoginView loginView = new LoginView(LECTURER); //STUDENT = lecturer
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword(""); //Empty password

        //Create controller and simulate lecturer login LECTURER option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, LECTURER));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }

    @Test
    public void testAdminLoginEmpty() {
        //Set current users to only lecturer for this test
        AdminDAO adminDAO = new AdminDAO();
        UserController.setCurrentUsers(adminDAO.getAllUsers());

        LoginView loginView = new LoginView(ADMIN); //ADMIN = admin
        loginView.setVisible(false); //prevent GUI from showing
        loginView.setUsername(""); //Empty ID
        loginView.setPassword(""); //Empty password

        //Create controller and simulate admin login ADMIN option
        LoginController controller = new LoginController(loginView);
        controller.actionPerformed(new ActionEvent(loginView, ActionEvent.ACTION_PERFORMED, ADMIN));

        //Assert null that login failed
        assertNull("Login should fail with wrong ID", UserController.getCurrentUser());
    }
}
