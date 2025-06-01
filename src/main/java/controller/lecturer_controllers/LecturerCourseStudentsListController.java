/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.lecturer_controllers;

import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
import utility_classes.ValidationUtil;
import controller.UserController;
import dao.CourseDAO;
import dao.StudentDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JOptionPane;
import objects.Course;
import objects.Lecturer;
import objects.Student;
import view.lecturer_view.LecturerCourseStudentsListView;
import view.lecturer_view.LecturerManageCoursesView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Lecturer student related
 * implementations.
 *
 */
public class LecturerCourseStudentsListController implements ActionListener {

    private Course currentCourse;
    private HashMap<Integer, Float> studentGrades;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;

    public LecturerCourseStudentsListController(Course currentCourse, HashMap<Integer, Float> studentGrades) {
        this.currentCourse = currentCourse;
        this.studentGrades = studentGrades;
        this.courseDAO = new CourseDAO();
        this.studentDAO = new StudentDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*
        get respective command depending on button clicked
        e.g. "g,24237573" ==> update grade for student with that id
        index 0 = g & index 1 = 24237573
         */
        String[] command = e.getActionCommand().split(",");

        switch (command[0]) {
            case "g": //update chosen student's grade
                this.updateStudentGrade((Student) studentDAO.getById(Integer.parseInt(command[1])));
                break;
            case "s": //sign off chosen student
                this.signOffStudent((Student) studentDAO.getById(Integer.parseInt(command[1])));
                break;
            case "b": //go back to the dashboard for managing courses
                NavigationUtil.newFrame(new LecturerManageCoursesView((Lecturer) UserController.getCurrentUser()));
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }

    //helper method for handling the logic of updating a student's grade
    private void updateStudentGrade(Student updatedStudent) {

        //prompting lecturer for new grade
        String lecturerInput = PopUpUtil.displayInputInfo("Please enter the new grade (0 - 100) for:\n"
                + updatedStudent.getId() + ", " + updatedStudent.getFirstName() + " " + updatedStudent.getLastName());

        if (lecturerInput == null) { //if lecturer cancels
            return;

        } else if (ValidationUtil.checkFloatRange(lecturerInput, 0, 100)) { //else check that the grade is within valid range

            //if so, update the studentGrades hashmap
            studentGrades.put(updatedStudent.getId(), Float.parseFloat(lecturerInput));

            //update the grade within the EnrolledCourse table for that specific student + course
            courseDAO.updateEnrolledCourseTable(updatedStudent.getId(), currentCourse.getCourseId(), Float.parseFloat(lecturerInput));

            //success pop up + refreshing the student list
            PopUpUtil.displayInfo("Grade assigned successfully!");
            NavigationUtil.newFrame(new LecturerCourseStudentsListView(currentCourse));

        } else { //else alert the lecturer of invalid grade
            PopUpUtil.displayError("Grades must be a number assigned from 0 - 100.");
        }
    }

    //helper method for signing off a student
    private void signOffStudent(Student studentToSignOff) {

        //confirmation pop up for the lecturer
        int confirm = PopUpUtil.displayConfirmInfo(
                "Signing off " + studentToSignOff.getId() + ", " + studentToSignOff.getFirstName() + " " + studentToSignOff.getLastName() + "\n"
                + "\nThis will remove the student from your course,\n"
                + "and their grade will be transferred to their record.\n"
                + "\nProceed with sign off?");

        //if lecturer wishes to proceed
        if (confirm == JOptionPane.YES_OPTION) {

            //get the student's grade for the course from the hashmap
            Float studentGrade = studentGrades.get(studentToSignOff.getId());

            //add the student's grade for that specific course into the PreviousCourse table
            courseDAO.addCourseToTable(studentToSignOff.getId(), currentCourse.getCourseId(), studentGrade, true);

            //then remove the same entry from the EnrolledCourse table, as the student is now signed off
            courseDAO.removeCourseFromTable(studentToSignOff.getId(), currentCourse.getCourseId(), false);

            //success pop up + refreshing the student list
            PopUpUtil.displayInfo("Thank you, the student was successfully signed off!");
            NavigationUtil.newFrame(new LecturerCourseStudentsListView(currentCourse));
        }
    }
}
