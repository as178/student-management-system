/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.lecturer_controllers;

import concrete_classes.other.NavigationUtil;
import dao.CourseDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import objects.Lecturer;
import view.lecturer_view.LecturerDashboardView;
import view.lecturer_view.LecturerEditCoursesView;
import view.lecturer_view.LecturerManageCoursesView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Lecturer courses related
 * implementations.
 *
 */
public class LecturerManageCoursesController implements ActionListener {

    private LecturerManageCoursesView view1;
    //private StudentEnrollCoursesView view2;
    private Lecturer currentLecturer;
    private CourseDAO courseDAO;

    public LecturerManageCoursesController(LecturerManageCoursesView view, Lecturer currentLecturer) {
        this.view1 = view;
        this.currentLecturer = currentLecturer;
        this.courseDAO = new CourseDAO();
    }
//    
//    public StudentModifyCoursesController(StudentEnrollCoursesView view, Student currentStudent) {
//        this.view2 = view;
//        this.currentStudent = currentStudent;
//        this.courseDAO = new CourseDAO();
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] command = e.getActionCommand().split(",");

        switch (command[0]) {
            case "e":
                NavigationUtil.newFrame(new LecturerEditCoursesView(currentLecturer, courseDAO.getById(command[1])));
                break;

            case "l":
                //NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;

            case "b":
                NavigationUtil.newFrame(new LecturerDashboardView(currentLecturer));
                break;

            case "x":
                NavigationUtil.exitProgram();
                break;
        }
    }
}
