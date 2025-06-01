/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.lecturer_controllers;

import utility_classes.NavigationUtil;
import dao.CourseDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import objects.Lecturer;
import view.lecturer_view.LecturerCourseStudentsListView;
import view.lecturer_view.LecturerDashboardView;
import view.lecturer_view.LecturerEditCoursesView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Lecturer courses related
 * implementations.
 *
 */
public class LecturerManageCoursesController implements ActionListener {

    private Lecturer currentLecturer;
    private CourseDAO courseDAO;

    public LecturerManageCoursesController(Lecturer currentLecturer) {
        this.currentLecturer = currentLecturer;
        this.courseDAO = new CourseDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*
        get respective command depending on button clicked
        e.g. "e,COMP500" ==> edit COMP500
        index 0 = e & index 1 = COMP500
         */
        String[] command = e.getActionCommand().split(",");

        switch (command[0]) {
            case "e": //go to dashboard for editing course information for a chosen course
                NavigationUtil.newFrame(new LecturerEditCoursesView(courseDAO.getById(command[1])));
                break;
            case "l": //go to dashboard for seeing the list of students for a chosen course
                NavigationUtil.newFrame(new LecturerCourseStudentsListView(courseDAO.getById(command[1])));
                break;
            case "b": //go back to lecturer dashboard
                NavigationUtil.newFrame(new LecturerDashboardView(currentLecturer));
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }
}
