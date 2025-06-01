/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import utility_classes.NavigationUtil;
import objects.Student;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.student_view.StudentAcademicDetailsView;
import view.student_view.StudentViewCoursesView;
import view.student_view.StudentViewPrevCoursesView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Student, course viewing details
 * related implementations; viewing of current and previous courses.
 * 
 * This controller is used by:
 * - StudentViewCoursesView
 * - StudentViewPrevCoursesView
 *
 */
public class StudentViewCoursesController implements ActionListener {

    private Student currentStudent;

    public StudentViewCoursesController(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        switch (command) {
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
            case "b": //go back to academic details dashboard
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;
            case "c": //display dashboard for viewing currently enrolled courses
                NavigationUtil.newFrame(new StudentViewCoursesView(currentStudent));
                break;
            case "1": //display dashboard for viewing previously enrolled courses
                NavigationUtil.newFrame(new StudentViewPrevCoursesView(currentStudent));
                break;
        }
    }
}