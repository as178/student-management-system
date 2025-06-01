/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student_controllers;

import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
import objects.Student;
import controller.UserController;
import dao.CourseDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public StudentModifyMajorController(StudentModifyMajorView view, Student currentStudent) {
        this.view = view;
        this.currentStudent = currentStudent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //get respective command depending on button clicked

        switch (command) {
            case "c": //change student's major
                this.changeStudentMajor(); 
                break;
            case "b": //go back to the academic details section
                NavigationUtil.newFrame(new StudentAcademicDetailsView(currentStudent));
                break;
            case "x": //shutdown
                NavigationUtil.exitProgram();
                break;
        }
    }

    /*
    Helper method which takes care of the logic behind
    students changing their majors.
     */
    private void changeStudentMajor() {

        //get selected major from the view (radio buttons)
        String selectedMajor = view.getSelectedMajor();

        //when the student has selected a valid major
        if (selectedMajor != null) {

            //display confirmation pop up with information to student
            int choice = PopUpUtil.displayConfirmWarning(
                    "Changing your major will withdraw you from all your current courses.\n"
                    + "Your grades will appear as 'Withdrawn' and won't count towards your GPA.\n\n"
                    + "Are you sure you want to change your major to " + selectedMajor + "?"
            );

            //if the student wishes to proceed
            if (choice == JOptionPane.YES_OPTION) {

                //firstly change their major in memory (currentStudent object)
                currentStudent.setMajor(selectedMajor);

                //then load up the student's enrolled courses hashmap
                CourseDAO courseDAO = new CourseDAO();
                courseDAO.readStudentsCourses(currentStudent, currentStudent.getEnrolledCourses(), false);

                //if their hashmap isn't empty (i.e. they're currently enrolled into courses)
                if (!currentStudent.getEnrolledCourses().isEmpty()) {
                    //withdraw them from all their current courses
                    courseDAO.withdrawAllCourses(currentStudent);
                }

                //update the student table (saving student's new major) + success pop up message
                currentStudent.saveCurrrentUser();
                PopUpUtil.displayInfo("Your major was successfully updated to: " + selectedMajor + "!\n"
                        + "To enroll into your new courses select Back > Change Courses.");
            }

            //refresh the major view to reflect the new available majors (excluding the one the student is now taking)
            NavigationUtil.newFrame(new StudentModifyMajorView(currentStudent));

        } else {
            //else if the student hasn't selected anything and they click the confirm button, display an error message
            PopUpUtil.displayError("Please select a valid major.");
        }
    }
}
