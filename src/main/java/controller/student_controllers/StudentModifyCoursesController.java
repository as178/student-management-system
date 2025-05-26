/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import objects.Course;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
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
 * implementations.
 *
 */
public class StudentModifyCoursesController implements ActionListener {

    private StudentWithdrawCoursesView view1;
    private StudentEnrollCoursesView view2;
    private Student currentStudent;
    private CourseDAO courseDAO;

    public StudentModifyCoursesController(StudentWithdrawCoursesView view, Student currentStudent) {
        this.view1 = view;
        this.currentStudent = currentStudent;
        this.courseDAO = new CourseDAO();
    }
    
    public StudentModifyCoursesController(StudentEnrollCoursesView view, Student currentStudent) {
        this.view2 = view;
        this.currentStudent = currentStudent;
        this.courseDAO = new CourseDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] command = e.getActionCommand().split(",");

        switch (command[0]) {
            case "e1":
                NavigationUtil.newFrame(new StudentEnrollCoursesView(currentStudent));
                break;

            case "b1":
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;

            case "b2":
                NavigationUtil.newFrame(new StudentWithdrawCoursesView(currentStudent));
                break;

            case "w":
                int confirm = PopUpUtil.displayConfirmInfo("Are you sure you want\nto withdraw from " + command[1] + "?");

                if (confirm == JOptionPane.YES_OPTION) {
                    courseDAO.addCourseToTable(currentStudent.getId(), command[1], -1f, true);
                    courseDAO.removeCourseFromTable(currentStudent.getId(), command[1], false);
                    PopUpUtil.displayInfo("You have been successfully removed from " + command[1] + ".");
                    NavigationUtil.newFrame(new StudentWithdrawCoursesView(currentStudent));
                }
                break;

            case "e":
                int confirm2 = PopUpUtil.displayConfirmInfo("You have selected " + command[1] + ",\nproceed with enrollment?");

                if (confirm2 == JOptionPane.YES_OPTION && currentStudent.getEnrolledCourses().size() <= 8) {

                    if (currentStudent.getPreviousCourses().containsKey(command[1])) {
                        courseDAO.removeCourseFromTable(currentStudent.getId(), command[1], true);
                    }
                    courseDAO.addCourseToTable(currentStudent.getId(), command[1], null, false);
                    PopUpUtil.displayInfo("Enrollment successful!\nWelcome to " + command[1] + "!");
                    NavigationUtil.newFrame(new StudentEnrollCoursesView(currentStudent));
                    
                } else if (currentStudent.getEnrolledCourses().size() > 8){
                    PopUpUtil.displayWarning("You may only take\n8 courses at a time.");
                }
                break;

            case "x":
                NavigationUtil.exitProgram();
                break;
        }
    }

    //separate method for populating the availableCourses list
    public void getAvailableCourses(HashMap<String, Course> availableCourses, HashMap<String, Course> allCourses) {

        courseDAO.readStudentsCourses(currentStudent, currentStudent.getEnrolledCourses(), false);
        courseDAO.readStudentsCourses(currentStudent, currentStudent.getPreviousCourses(), true);

        //clear the list first, in case of updates
        availableCourses.clear();

        //iterate thorugh the allCourses hashset
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

    //separate method for populating the majorCourses list
    // - doesn't populate the list directly like the method above,
    //   instead it returns an arraylist of courses, because
    //   this list won't change/get updated while the student is logged in 
    public HashMap<String, Course> getMajorCourses(HashMap<String, Course> allMajorCourses, HashMap<String, Course> allCourses) {

        HashMap<String, Course> coursesList = new HashMap<String, Course>();

        //add courses to list if they have the same major as the student
        for (Course course : allCourses.values()) {
            if (course.getCourseMajor().equals(currentStudent.getMajor())) {
                coursesList.put(course.getCourseId(), course);
            }
        }

        return coursesList;
    }
}
