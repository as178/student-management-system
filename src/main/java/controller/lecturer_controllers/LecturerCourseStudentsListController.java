/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.lecturer_controllers;

import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import concrete_classes.other.ValidationUtil;
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
 * This Controller is responsible for any Lecturer courses related
 * implementations.
 *
 */
public class LecturerCourseStudentsListController implements ActionListener {

    private LecturerCourseStudentsListView view;
    private Course currentCourse;
    private HashMap<Integer, String> studentGrades;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;

    public LecturerCourseStudentsListController(
            LecturerCourseStudentsListView view, Course currentCourse, HashMap<Integer, String> studentGrades) {
        this.view = view;
        this.currentCourse = currentCourse;
        this.studentGrades = studentGrades;
        this.courseDAO = new CourseDAO();
        this.studentDAO = new StudentDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] command = e.getActionCommand().split(",");

        switch (command[0]) {
            case "g":
                this.updateStudentGrade((Student) studentDAO.getById(Integer.parseInt(command[1])));
                break;

            case "s":
                this.signOffStudent((Student) studentDAO.getById(Integer.parseInt(command[1])));
                break;

            case "b":
                NavigationUtil.newFrame(new LecturerManageCoursesView((Lecturer) UserController.getCurrentUser()));
                break;

            case "x":
                NavigationUtil.exitProgram();
                break;
        }
    }

    private void updateStudentGrade(Student updatedStudent) {

        String lecturerInput = PopUpUtil.displayInputInfo("Please enter the new grade (0 - 100) for:\n"
                + updatedStudent.getId() + ", " + updatedStudent.getFirstName() + " " + updatedStudent.getLastName());

        if (lecturerInput == null) {
            return;

        } else if (ValidationUtil.checkFloatRange(lecturerInput, 0, 100)) {

            studentGrades.put(updatedStudent.getId(), lecturerInput.trim());

            courseDAO.updateEnrolledCourseTable(updatedStudent.getId(), currentCourse.getCourseId(), Float.parseFloat(lecturerInput));

            PopUpUtil.displayInfo("Grade assigned successfully!");
            NavigationUtil.newFrame(new LecturerCourseStudentsListView(currentCourse));

        } else {
            PopUpUtil.displayError("Grades must be a number assigned from 0 - 100.");
        }
    }

    private void signOffStudent(Student studentToSignOff) {

        int confirm = PopUpUtil.displayConfirmInfo(
                "Signing off " + studentToSignOff.getId() + ", " + studentToSignOff.getFirstName() + " " + studentToSignOff.getLastName() + "\n"
                + "This will remove the student from your course,\n"
                + "and their grade will be transferred to their record.\n"
                + "\nProceed with sign off?");

        if (confirm == JOptionPane.YES_OPTION) {

            String studentGrade = studentGrades.get(studentToSignOff.getId());
            courseDAO.addCourseToTable(studentToSignOff.getId(), currentCourse.getCourseId(), Float.parseFloat(studentGrade), true);
            courseDAO.removeCourseFromTable(studentToSignOff.getId(), currentCourse.getCourseId(), false);
            
            PopUpUtil.displayInfo("Thank you, the student was successfully signed off!");
            NavigationUtil.newFrame(new LecturerCourseStudentsListView(currentCourse));
        }
    }
}