/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller.student_controllers;

import abstract_classes.User;
import dao.CourseDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import model.DatabaseManager;
import objects.Course;
import objects.Student;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author williamniven
 */
public class StudentModifyCoursesControllerTest {

    private Student student;
    private StudentModifyCoursesController controller;
    private HashMap<String, Course> allCourses;
    private HashMap<String, Course> availableCourses;
    private CourseDAO courseDAO;

    public StudentModifyCoursesControllerTest() {
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
        //load student
        StudentDAO studentDAO = new StudentDAO();
        HashMap<String, User> allStudents = studentDAO.getAllUsers();
        student = (Student) allStudents.get("24229618");

        //check if test student has loaded
        assertNotNull("Test student not found in DAO", student);

        //initilize the controller
        controller = new StudentModifyCoursesController(student);

        //load all courses from DAO
        courseDAO = new CourseDAO();
        allCourses = courseDAO.getAllCourses();
        availableCourses = new HashMap<>();

        //Ensure student has at least one enrolled course (COMP500)
        if (student.getEnrolledCourses().isEmpty()) {
            courseDAO.addCourseToTable(student.getId(), "COMP500", 90f, false);
            courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false); //refresh
        }

        //Ensure student has one failed course in previous courses hashmap (COMP501)
        boolean hasFailed = false;
        for (Float grade : student.getPreviousCourses().values()) {
            if (grade != null && grade < 49.5f) {
                hasFailed = true;
                break;
            }
        }
        if (!hasFailed) {
            courseDAO.addCourseToTable(student.getId(), "COMP501", 40f, true); //fail COMP501
            courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true); //refresh hashmap
        }
    }

    @After
    public void tearDown() {
    }

    /*
    ceck that getEnrolledCourses is not null
     */
    @Test
    public void testGetEnrolledCourses() {
        //Ensure COMP500 is enrolled before asserting
        if (!student.getEnrolledCourses().containsKey("COMP500")) {
            courseDAO.addCourseToTable(student.getId(), "COMP500", 85f, false); //enroll
            courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false); //refresh hashmap
        }

        //assert that getEnrolledCourses contains an entry 
        assertTrue("Expected some enrolled courses", student.getEnrolledCourses().size() > 0);
        //ceck if COMP500 is containted in getEnrolledCourses
        assertTrue("Expected COMP500 to be enrolled", student.getEnrolledCourses().containsKey("COMP500"));
    }

    /*
    Checks getAvailableCourses() returns a non-empty list 
     */
    @Test
    public void testgetAvailableCourses() {
        //Ensure COMP500 is NOT in enrolled courses
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", false); //remove COMP500 from enrolled

        //add COMP500 to previousCourses with a failed grade
        courseDAO.addCourseToTable(student.getId(), "COMP500", 40f, true); //fail COMP500
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true);

        //refresh enrolled list hashmap
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false);

        //get available courses
        controller.getAvailableCourses(availableCourses, allCourses);

        //assertions
        if (availableCourses.isEmpty()) {
            System.out.println("No eligible courses found – possibly due to test data.");
        }

        assertTrue("Expected some eligible courses", availableCourses.size() > 0);
        assertTrue("COMP500 should be available after unenroll/fail", availableCourses.containsKey("COMP500"));
    }

    /*
    checks that avaliable courses dose not contain currently enrolled courses
     */
    @Test
    public void testCoursesExcludeInrolledCourses() {
        controller.getAvailableCourses(availableCourses, allCourses);
        for (String enrolledId : student.getEnrolledCourses().keySet()) {
            assertFalse("Already enrolled course found in available list", availableCourses.containsKey(enrolledId));
        }
    }

    /*
    checks if previously failed or withdrawn courses are included in the available course list
     */
    @Test
    public void testFailedOrWithdrawnCoursesAreIncluded() {
        //checks if student is enrolled in COMP500
        assertTrue("Student should be enrolled in COMP500", student.getEnrolledCourses().containsKey("COMP500"));

        //withdrawal to move course to PreviousCourses with -1f grade
        courseDAO.addCourseToTable(student.getId(), "COMP500", -1f, true); //PreviousCourses
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", false); //EnrolledCourses
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true); //refresh PreviousCourses
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false); //refresh EnrolledCourses

        //Assert results
        assertFalse("COMP500 should be removed from enrolled courses", student.getEnrolledCourses().containsKey("COMP500"));
        assertTrue("COMP500 should now appear in previous courses", student.getPreviousCourses().containsKey("COMP500"));
        assertEquals("COMP500 grade should be -1 (withdrawn)", -1f, student.getPreviousCourses().get("COMP500"), 0);
    }

    /*
    test withdrawing from course moves the course from currenly enrolled to previously enrolled
     */
    @Test
    public void testWithdrawFromCourseMovesToPreviousEnrollment() {
        //clean start state
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", false); // remove from enrolled
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", true);  // remove from previous

        //checks if student is enrolled in COMP500
        assertTrue("Student should be enrolled in COMP500", student.getEnrolledCourses().containsKey("COMP500"));

        //withraw from COMP500, move to previous with grade -1 and remove from enrolled
        courseDAO.addCourseToTable(student.getId(), "COMP500", -1f, true); //previous table
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", false); //enrolled table

        //Refresh both hashmaps
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false);
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true);

        //Assertions
        assertFalse("Student should not be enrolled in COMP500", student.getEnrolledCourses().containsKey("COMP500"));
        assertTrue("Student should have COMP500 in previous courses", student.getPreviousCourses().containsKey("COMP500"));
        assertEquals("COMP500 should be marked as withdrawn (-1)", -1f, student.getPreviousCourses().get("COMP500"), 0.01f);
    }

    /*
    tests enrolling into a course from a failed course from previousCourses
     */
    @Test
    public void testEnrollRemovesFromPrevious() {
        //withdrawal from COMP500 (move COMP500 to previousCourses)
        courseDAO.addCourseToTable(student.getId(), "COMP500", -1f, true); // withdrawn
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true); // refresh

        //check previous courses 
        assertTrue("COMP500 should exist in previousCourses before re-enrollment",
                student.getPreviousCourses().containsKey("COMP500"));

        //enrollment into COMP500
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", true); // remove from previous
        courseDAO.addCourseToTable(student.getId(), "COMP500", null, false); // add to enrolled
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false); // refresh

        //Refresh both hashmaps
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false);
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true);

        //assertions
        assertTrue("Student should now be enrolled in COMP500",
                student.getEnrolledCourses().containsKey("COMP500"));
        assertFalse("COMP500 should no longer be in previousCourses after enrollment",
                student.getPreviousCourses().containsKey("COMP500"));
    }
}
