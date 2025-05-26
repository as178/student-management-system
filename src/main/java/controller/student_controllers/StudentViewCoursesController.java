/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import concrete_classes.other.NavigationUtil;
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
 * related implementations.
 *
 */
public class StudentViewCoursesController implements ActionListener {

    private StudentViewCoursesView viewCurrent;
    private StudentViewPrevCoursesView viewPrevious;
    private Student currentStudent;

    public StudentViewCoursesController(StudentViewCoursesView view, Student currentStudent) {
        this.viewCurrent = view;
        this.currentStudent = currentStudent;
    }

    public StudentViewCoursesController(StudentViewPrevCoursesView view, Student currentStudent) {
        this.viewPrevious = view;
        this.currentStudent = currentStudent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "x":
                NavigationUtil.exitProgram();
                break;
            case "b":
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;
            case "c":
                NavigationUtil.newFrame(new StudentViewCoursesView(currentStudent));
                break;
            case "1":
                NavigationUtil.newFrame(new StudentViewPrevCoursesView(currentStudent));
                break;
        }
    }
}