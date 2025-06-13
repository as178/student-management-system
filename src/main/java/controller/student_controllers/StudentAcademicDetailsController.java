/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import utility_classes.NavigationUtil;
import objects.Student;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private Student currentStudent;

    public StudentAcademicDetailsController(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        switch (command) {
            case "1": //go to course viewing dashboard
                NavigationUtil.newFrame(new StudentViewCoursesView(currentStudent));
                break;
            case "2": //go to dashboard for changing majors
                NavigationUtil.newFrame(new StudentModifyMajorView(currentStudent));
                break;
            case "3": //go to dashboard for withdrawing courses
                NavigationUtil.newFrame(new StudentWithdrawCoursesView(currentStudent));
                break;
            case "b": //go back to the student dashboard
                currentStudent.userMainDashboard();
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }
}
