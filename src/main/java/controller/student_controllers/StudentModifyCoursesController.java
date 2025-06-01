/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import objects.Course;
import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
import objects.Student;
import dao.CourseDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JOptionPane;
import view.student_view.StudentAcademicDetailsView;
import view.student_view.StudentEnrollCoursesView;
import view.student_view.StudentWithdrawCoursesView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Student courses related
 * implementations (enrolling, withdrawing and/or displaying courses).
 *
 * This controller is used by:
 * - StudentWithdrawCoursesView
 * - StudentEnrollCoursesView
 *
 */
public class StudentModifyCoursesController implements ActionListener {

    private Student currentStudent;
    private CourseDAO courseDAO;

    public StudentModifyCoursesController(Student currentStudent) {
        this.currentStudent = currentStudent;
        this.courseDAO = new CourseDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        get respective command depending on button clicked
        e.g. "w,COMP500" ==> withdraw from COMP500
        index 0 = w & index 1 = COMP500
         */
        String[] command = e.getActionCommand().split(",");

        switch (command[0]) {
            case "e1": //display dashboard for enrolling into new courses
                NavigationUtil.newFrame(new StudentEnrollCoursesView(currentStudent));
                break;

            case "b1": //go back to the academic details dashboard
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;

            case "b2": //go back to dashboard where students can withdraw from courses
                NavigationUtil.newFrame(new StudentWithdrawCoursesView(currentStudent));
                break;

            case "w": //withdraw student from a specific course
                this.withdrawFromCourse(command[1]);
                break;

            case "e": //enroll student into chosen course
                this.enrollIntoCourse(command[1]);
                break;

            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }

    //separate method for withdrawing a student from a selected course
    private void withdrawFromCourse(String courseId) {
        //show a pop up to confirm choice
        int confirm = PopUpUtil.displayConfirmInfo("Are you sure you want\nto withdraw from " + courseId + "?");

        //if student confirms choice then:
        if (confirm == JOptionPane.YES_OPTION) {

            //add course the student is withdrawing from into the PreviousCourse table (grade -1 ==> withdrawal)
            courseDAO.addCourseToTable(currentStudent.getId(), courseId, -1f, true);

            //remove the course from the EnrolledCourse table 
            courseDAO.removeCourseFromTable(currentStudent.getId(), courseId, false);

            //display a successs pop up and refresh the view
            PopUpUtil.displayInfo("You have been successfully removed from " + courseId + ".");
            NavigationUtil.newFrame(new StudentWithdrawCoursesView(currentStudent));
        }
    }

    //separate method for enrolling a student into a chosen course
    private void enrollIntoCourse(String courseId) {
        //show a pop up to confirm choice of selection
        int confirm = PopUpUtil.displayConfirmInfo("You have selected " + courseId + ",\nproceed with enrollment?");

        //if the student wishes to proceed (and they're taking less than 8 courses):
        if (confirm == JOptionPane.YES_OPTION && currentStudent.getEnrolledCourses().size() <= 8) {

            //if the student was previously taking the course
            if (currentStudent.getPreviousCourses().containsKey(courseId)) {
                //remove that entry from the PreviousCourse table
                courseDAO.removeCourseFromTable(currentStudent.getId(), courseId, true);
            }

            //add the course to the EnrolledCourse table (grade null ==> unassigned)
            courseDAO.addCourseToTable(currentStudent.getId(), courseId, null, false);

            //display a successs pop up and refresh the view
            PopUpUtil.displayInfo("Enrollment successful!\nWelcome to " + courseId + "!");
            NavigationUtil.newFrame(new StudentEnrollCoursesView(currentStudent));

        } else if (currentStudent.getEnrolledCourses().size() > 8) {
            //show pop up in case student is already taking 8 courses
            PopUpUtil.displayWarning("You may only take\n8 courses at a time.");
        }
    }

    //separate method for populating the availableCourses hashmap
    public void getAvailableCourses(HashMap<String, Course> availableCourses, HashMap<String, Course> allCourses) {

        //populate student's courses hashmaps
        courseDAO.readStudentsCourses(currentStudent, currentStudent.getEnrolledCourses(), false);
        courseDAO.readStudentsCourses(currentStudent, currentStudent.getPreviousCourses(), true);

        //clear the list first, in case of updates
        availableCourses.clear();

        //iterate thorugh the allCourses hashmap
        for (Course course : allCourses.values()) { //add course to availableCourses list IF:
            if (course.getCourseMajor().equals(currentStudent.getMajor()) //course is in the student's major
                    && !currentStudent.getEnrolledCourses().containsKey(course.getCourseId()) //student isn't already enrolled in the course
                    && (!currentStudent.getPreviousCourses().containsKey(course.getCourseId()) //student hasn't taken the course yet
                    || currentStudent.getPreviousCourses().get(course.getCourseId()) < 49.5f)) { //or if they have, they withdrew from it/failed

                if (course.getCoursePrerequisite() == null) { //course has no prerequisites
                    availableCourses.put(course.getCourseId(), course);
                } else if (currentStudent.getPreviousCourses().containsKey(course.getCoursePrerequisite()) //or if it does, ensure:
                        && currentStudent.getPreviousCourses().get(course.getCoursePrerequisite()) >= 49.5f) { //student passed them & didn't withdraw
                    availableCourses.put(course.getCourseId(), course);
                }
            }
        }
    }

    //separate method for populating the allMajorCourses hashmap
    public void getMajorCourses(HashMap<String, Course> allMajorCourses, HashMap<String, Course> allCourses) {

        //iterate through all courses
        for (Course course : allCourses.values()) {
            
            //add courses to hashmap if they have the same major as the student
            if (course.getCourseMajor().equals(currentStudent.getMajor())) {
                allMajorCourses.put(course.getCourseId(), course);
            }
        }
    }
}
