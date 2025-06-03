/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller.lecturer_controllers;

import abstract_classes.User;
import controller.student_controllers.StudentModifyCoursesController;
import dao.CourseDAO;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import model.DatabaseManager;
import objects.Course;
import objects.Lecturer;
import objects.Student;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;

/**
 *
 * @author williamniven
 */
public class LecturerManageCoursesControllerTest {

    private Lecturer lecturer;
    private CourseDAO courseDAO;
    private HashMap<String, Course> coursesTaught;

    public LecturerManageCoursesControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        //Initialize DB connection for DAO methods
        DatabaseManager.initialiseConnection();

        //disable visible GUI
        utility_classes.NavigationUtil.testMode = true;
        utility_classes.PopUpUtil.testMode = true; //disable pop ups
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //load lecturer
        LecturerDAO lecturerDAO = new LecturerDAO();
        HashMap<String, User> allUsers = lecturerDAO.getAllUsers();
        lecturer = (Lecturer) allUsers.get("14568912");

        //check if test student has loaded
        assertNotNull("Test lecturer not found in DAO", lecturer);

        //load all courses from DAO
        courseDAO = new CourseDAO();
        courseDAO.readLecturerCourses(lecturer);
        coursesTaught = lecturer.getCoursesTaught();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetLecturerCoursesTaught() {
        assertTrue("Lecturer should have courses", lecturer.getCoursesTaught().size() > 0);
        assertTrue("Lecturer should teach COMP500", lecturer.getCoursesTaught().containsKey("COMP500"));
        assertTrue("Lecturer should teach COMP606", lecturer.getCoursesTaught().containsKey("COMP606"));
    }

    @Test
    public void testGetEnrolledStudentsPerCourse() {
        HashMap<Integer, Float> comp500Students = courseDAO.readEnrolledStudentsGrades("COMP500");
        assertEquals("COMP500 should have two students", 2, comp500Students.size());

        HashMap<Integer, Float> comp606Students = courseDAO.readEnrolledStudentsGrades("COMP606");
        assertEquals("COMP606 should have one student", 1, comp606Students.size());

        //verify Ava Scott is enrolled in COMP500
        Student avaScott = (Student) new StudentDAO().getById(21837645); // Use John Doe's actual ID
        assertTrue("John Doe should be enrolled in COMP500", comp500Students.containsKey(avaScott.getId()));

        //verify John Doe is enrolled in COMP606
        Student johnDoe = (Student) new StudentDAO().getById(24229618); //John Doe ID
        assertTrue("John Doe should be enrolled in COMP606", comp606Students.containsKey(johnDoe.getId()));
    }

    @Test
    public void testUpdateStudentGradeInCourse() {
        Course comp500 = courseDAO.getById("COMP500");
        assertNotNull("Course COMP500 should exist", comp500);

        HashMap<Integer, Float> studentGrades = courseDAO.readEnrolledStudentsGrades("COMP500");

        LecturerCourseStudentsListController controller = new LecturerCourseStudentsListController(comp500, studentGrades);

        Student student = (Student) new StudentDAO().getById(studentGrades.keySet().iterator().next());
        Float newGrade = 75f;

        // Directly update the grade
        courseDAO.updateEnrolledCourseTable(student.getId(), comp500.getCourseId(), newGrade);
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false);

        assertEquals("Student grade should be updated", newGrade, student.getEnrolledCourses().get("COMP500"), 0.01f);
    }

    @Test
    public void testCourseUpdateDetails() {
        Course comp500 = courseDAO.getById("COMP500");
        assertNotNull("Course COMP500 should exist", comp500);

        //get original values
        String originalDescription = comp500.getCourseDescription();
        String originalName = comp500.getCourseName();
        int originalEstimatedHours = comp500.getCourseEstimatedHours();

        String updatedName = "Updated Course Name";
        String updatedDescription = "Updated course description for testing";
        int updatedEstimatedHours = 100;

        comp500.setCourseName(updatedName);
        comp500.setCourseDescription(updatedDescription);
        comp500.setCourseEstimatedHours(updatedEstimatedHours);

        courseDAO.updateCourse(comp500);
        comp500 = courseDAO.getById("COMP500");

        assertEquals("Course name should be updated", updatedName, comp500.getCourseName());
        assertEquals("Estimated hours should be updated", updatedEstimatedHours, comp500.getCourseEstimatedHours());
        assertEquals("Description should be updated", updatedDescription, comp500.getCourseDescription());

        //cleanup, return course values to original 
        comp500.setCourseName(originalName);
        comp500.setCourseDescription(originalDescription);
        comp500.setCourseEstimatedHours(originalEstimatedHours);

        courseDAO.updateCourse(comp500);
    }
}
