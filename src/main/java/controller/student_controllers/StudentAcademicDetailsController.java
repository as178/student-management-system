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
import view.student_view.StudentDashboardView;
import view.student_view.StudentModifyMajorView;
import view.student_view.StudentViewCoursesView;
import view.student_view.StudentWithdrawCoursesView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Student academic details related
 * implementations.
 *
 */
public class StudentAcademicDetailsController implements ActionListener {

    private StudentAcademicDetailsView view;
    private Student currentStudent;

    public StudentAcademicDetailsController(StudentAcademicDetailsView view, Student currentStudent) {
        this.view = view;
        this.currentStudent = currentStudent;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("x".equalsIgnoreCase(command)) {
            NavigationUtil.exitProgram();
            return;
        }

        if ("b".equalsIgnoreCase(command)) {
            NavigationUtil.newFrame(new StudentDashboardView(currentStudent));
        }

        switch (command) {
            case "1": 
                NavigationUtil.newFrame(new StudentViewCoursesView(currentStudent));
                break;
            case "2": 
                NavigationUtil.newFrame(new StudentModifyMajorView(currentStudent));
                break;
            case "3": 
                NavigationUtil.newFrame(new StudentWithdrawCoursesView(currentStudent));
                break;
        }
    }
}