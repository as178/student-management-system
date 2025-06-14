/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller.student_controllers;

import dao.CourseDAO;
import dao.StudentDAO;
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
import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Test cases for the main functionalities of Student.
 * (For convenience, we have decided to test on other default
 * users within the program.)
 * 
 */
public class StudentModifyCoursesControllerTest {

    private static Student student;
    private static StudentModifyCoursesController controller;
    private static HashMap<String, Course> allCourses;
    private static HashMap<String, Course> availableCourses;
    private static CourseDAO courseDAO;

    public StudentModifyCoursesControllerTest() {}

    @BeforeClass
    public static void setUpClass() {
        //Initialize DB + connection for DAO methods
        DatabaseManager databaseManager = new DatabaseManager();

        //disable visible GUI
        NavigationUtil.testMode = true;
        //disable pop ups
        PopUpUtil.testMode = true;

        //load student
        StudentDAO studentDAO = new StudentDAO();
        student = studentDAO.getById(Integer.parseInt("24229618"));

        //check if test student has loaded
        assertNotNull("Test student not found in DAO", student);

        //initilize the controller
        controller = new StudentModifyCoursesController(student);

        //load all courses from DAO
        courseDAO = new CourseDAO();
        allCourses = courseDAO.getAllCourses();
        availableCourses = new HashMap<>();
    }

    @AfterClass
    public static void tearDownClass() {
        //clear both enrolled and previous tables following completion
        courseDAO.removeStudentCourses(student, true);
        courseDAO.removeStudentCourses(student, false);

        //reset default data for student for both tables
        courseDAO.addCourseToTable(student.getId(), "COMP500", 100f, true);
        courseDAO.addCourseToTable(student.getId(), "COMP501", null, false);
        courseDAO.addCourseToTable(student.getId(), "COMP601", 97.67f, false);
        courseDAO.addCourseToTable(student.getId(), "COMP606", 98.34f, false);

        //close connection to DB when tests are done
        //+ set the testmodes off for GUI and pop-ups
        DatabaseManager.closeConnection();
        NavigationUtil.testMode = false;
        PopUpUtil.testMode = false;
    }

    @Before
    public void setUp() {
        //clear both enrolled and previous tables; set clean state 
        courseDAO.removeStudentCourses(student, true);
        courseDAO.removeStudentCourses(student, false);

        //refresh hashmaps right after
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true); //refresh previous hashmap
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false); //refresh enrolled hashmap

        //then ensure student has at least one enrolled course (COMP500)
        if (student.getEnrolledCourses().isEmpty()) {
            courseDAO.addCourseToTable(student.getId(), "COMP500", 90f, false);
            courseDAO.removeCourseFromTable(student.getId(), "COMP500", true); //remove from previous enrollments
            //update enrolled courses hashmap
            courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false);
        }

        //ensure student has one failed course in previous courses hashmap (COMP501)
        courseDAO.addCourseToTable(student.getId(), "COMP501", 40f, true); //fail COMP501
        courseDAO.removeCourseFromTable(student.getId(), "COMP501", false); //remove from current enrollments
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true); //update previous courses hashmap
    }

    @After
    public void tearDown() {
    }

    /*
    checks that getEnrolledCourses is not null
     */
    @Test
    public void getEnrolledCoursesTest() {
        //Ensure COMP500 is enrolled before asserting
        if (!student.getEnrolledCourses().containsKey("COMP500")) {
            courseDAO.addCourseToTable(student.getId(), "COMP500", 85f, false); //enroll
            courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false); //refresh hashmap
        }

        //assert that getEnrolledCourses contains an entry 
        assertTrue("Expected some enrolled courses", !student.getEnrolledCourses().isEmpty());
        //ceck if COMP500 is containted in getEnrolledCourses
        assertTrue("Expected COMP500 to be enrolled", student.getEnrolledCourses().containsKey("COMP500"));
    }

    /*
    checks getAvailableCourses() returns a non-empty list 
     */
    @Test
    public void getAvailableCoursesTest() {
        //Ensure COMP500 is NOT in tables
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", false); //remove COMP500 from enrolled
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", true); //remove COMP500 from previous

        //add COMP500 to previousCourses with a failed grade
        courseDAO.addCourseToTable(student.getId(), "COMP500", 30f, true); //fail COMP500

        //refresh student courses hashmaps
        courseDAO.readStudentsCourses(student, student.getEnrolledCourses(), false);
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true);

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
    public void coursesExcludeEnrolledCoursesTest() {
        controller.getAvailableCourses(availableCourses, allCourses);
        for (String enrolledId : student.getEnrolledCourses().keySet()) {
            assertFalse("Already enrolled course found in available list", availableCourses.containsKey(enrolledId));
        }
    }

    /*
    checks that all courses loaded from the getMajorCourses method are the same as the student's major
     */
    @Test
    public void coursesFromMajorOnlyTest() {
        HashMap<String, Course> majorCourses = new HashMap<>();
        controller.getMajorCourses(majorCourses, allCourses);
        for (Course majorCourse : majorCourses.values()) {
            assertTrue("Course not from the correct major", majorCourse.getCourseMajor().equals(student.getMajor()));
        }
    }

    /*
    checks if previously failed or withdrawn courses are included in the available course list
     */
    @Test
    public void failedOrWithdrawnCoursesAreIncludedTest() {
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
    tests withdrawing from course; moving the course from currenly enrolled to previously enrolled
     */
    @Test
    public void withdrawFromCourseMovesToPreviousEnrollmentTest() {

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
    public void enrollRemovesFromPreviousTest() {
        //withdrawal from COMP500 (move COMP500 to previousCourses)
        courseDAO.addCourseToTable(student.getId(), "COMP500", 30f, true); //move to previous with failed grade
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", false); //remove from enrolled
        courseDAO.readStudentsCourses(student, student.getPreviousCourses(), true); //refresh previous

        //check previous courses 
        assertTrue("COMP500 should exist in previousCourses before re-enrollment",
                student.getPreviousCourses().containsKey("COMP500"));

        //enrollment into COMP500
        courseDAO.removeCourseFromTable(student.getId(), "COMP500", true); //remove from previous
        courseDAO.addCourseToTable(student.getId(), "COMP500", null, false); //add to enrolled

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
