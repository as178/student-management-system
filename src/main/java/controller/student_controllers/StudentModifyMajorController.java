/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import objects.Student;
import controller.UserController;
import dao.CourseDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import view.student_view.StudentAcademicDetailsView;
import view.student_view.StudentModifyMajorView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Controller is responsible for any Student major related implementations.
 *
 */
public class StudentModifyMajorController implements ActionListener {

    private StudentModifyMajorView view;
    private Student currentStudent;
    private ArrayList<String> listOfMajors;

    public StudentModifyMajorController(StudentModifyMajorView view, Student currentStudent, ArrayList<String> listOfMajors) {
        this.view = view;
        this.currentStudent = currentStudent;
        this.listOfMajors = listOfMajors;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "c":

                String selectedMajor = view.getSelectedMajor();

                if (selectedMajor != null) {
                    int choice = PopUpUtil.displayConfirmWarning(
                            "Changing your major will withdraw you from all your current courses.\n"
                            + "Your grades will appear as 'Withdrawn' and won't count towards your GPA.\n\n"
                            + "Are you sure you want to change your major to " + selectedMajor + "?"
                    );

                    if (choice == JOptionPane.YES_OPTION) {

                        currentStudent.setMajor(selectedMajor);
                        CourseDAO courseDAO = new CourseDAO();
                        courseDAO.readStudentsCourses(currentStudent, currentStudent.getEnrolledCourses(), false);

                        if (!currentStudent.getEnrolledCourses().isEmpty()) {
                            courseDAO.withdrawAllCourses(currentStudent);
                        }
                        UserController.saveCurrrentUser();

                        PopUpUtil.displayInfo("Your major was successfully updated to: " + selectedMajor + "!\n"
                                + "To enroll into your new courses select Back > Change Courses.");
                    }

                    NavigationUtil.newFrame(new StudentModifyMajorView(currentStudent));

                } else {
                    PopUpUtil.displayError("Please select a valid major.");
                }

                break;

            case "b":
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;
            case "x":
                NavigationUtil.exitProgram();
                break;
        }
    }
}
