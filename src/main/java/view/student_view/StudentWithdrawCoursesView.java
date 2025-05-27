/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.student_view;

import objects.Course;
import objects.Student;
import controller.student_controllers.StudentModifyCoursesController;
import dao.CourseDAO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays the student's current enrollments and provides them with
 * further options (to enroll into more courses or withdraw from enrollments).
 * 
 * Controller: StudentModifyCoursesController
 *
 */
public class StudentWithdrawCoursesView extends JFrame {

    private StudentModifyCoursesController controller;
    private Student currentStudent;
    private CourseDAO courseDAO = new CourseDAO();
    private HashMap<String, Course> allCourses;

    public StudentWithdrawCoursesView(Student currentStudent) {
        this.currentStudent = currentStudent;
        this.controller = new StudentModifyCoursesController(this, currentStudent);
        this.allCourses = courseDAO.getAllCourses();

        setTitle("Student Management System: Modify Courses");
        setLayout(new BorderLayout());

        //Title panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //Sub-texts for title panel
        JLabel headerLabel1 = new JLabel("Your current courses are shown below, you may", SwingConstants.CENTER);
        JLabel headerLabel2 = new JLabel("withdraw from any course or see options below.", SwingConstants.CENTER);

        headerLabel1.setFont(new Font("Monospaced", Font.BOLD, 17));
        headerLabel2.setFont(new Font("Monospaced", Font.BOLD, 17));

        //Adding labels to title panel ==> frame
        headerPanel.add(headerLabel1);
        headerPanel.add(headerLabel2);
        this.add(headerPanel, BorderLayout.NORTH);

        //Course/centre panel
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        coursePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //Load up student's currently enrolled courses hashmap
        courseDAO.readStudentsCourses(currentStudent, currentStudent.getEnrolledCourses(), false);

        //If empty = appropriate message 
        if (currentStudent.getEnrolledCourses().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("> No enrollments yet . . .");
            noCoursesLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
            coursePanel.add(noCoursesLabel);

        } else {
            //else iterate through student's courses and display them
            //each with a withdraw button next to it
            for (String courseId : currentStudent.getEnrolledCourses().keySet()) {

                Course currentCourse = allCourses.get(courseId); //get course

                if (currentCourse.getCourseId().equals(courseId)) { //if student is taking it

                    //align row to the left hand side of the frame 
                    JPanel row = new JPanel();
                    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                    row.setAlignmentX(Component.LEFT_ALIGNMENT);

                    //withdraw button config
                    JButton withdrawButton = new JButton("Withdraw");
                    withdrawButton.setFont(new Font("Monospaced", Font.BOLD, 14));
                    withdrawButton.setActionCommand("w," + currentCourse.getCourseId()); //action command for controller, includes courseId
                    withdrawButton.addActionListener(controller);

                    //Course label to display course id + name
                    JLabel courseLabel = new JLabel("  ||  " + currentCourse.getCourseId()
                            + ", " + currentCourse.getCourseName());
                    courseLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));

                    //Add button and label to row ==> course panel + spacing
                    row.add(withdrawButton);
                    row.add(courseLabel);
                    row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

                    coursePanel.add(row);
                    coursePanel.add(Box.createVerticalStrut(6)); 

                }
            }
        }

        //Make course panel scrollable + add to frame
        JScrollPane scrollPane = new JScrollPane(coursePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);

        //Button config + styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton enrollButton = new JButton("Enroll into Courses");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        //Action commands for controller to handle
        enrollButton.setActionCommand("e1");
        backButton.setActionCommand("b1");
        exitButton.setActionCommand("x");

        JButton[] buttons = {enrollButton, backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            button.addActionListener(controller);
            buttonPanel.add(button);
        }

        //Adding button panel to the frame
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
