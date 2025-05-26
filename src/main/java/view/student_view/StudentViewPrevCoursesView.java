/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.student_view;

import objects.Course;
import objects.Lecturer;
import concrete_classes.other.GradesUtil;
import objects.Student;
import controller.student_controllers.StudentViewCoursesController;
import dao.CourseDAO;
import dao.LecturerDAO;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This view displays detailed information about the courses the student was
 * previously taking.
 *
 * Controller: StudentViewCoursesController
 *
 */
public class StudentViewPrevCoursesView extends JFrame {

    private Student currentStudent;

    public StudentViewPrevCoursesView(Student student) {
        //variables + daos for easy access 
        this.currentStudent = student;
        CourseDAO courseDAO = new CourseDAO();
        LecturerDAO lecturerDAO = new LecturerDAO();

        //load up all courses into hashmap
        HashMap<String, Course> allCourses = courseDAO.getAllCourses();

        //load up student's previously enrolled courses hashmap        
        courseDAO.readStudentsCourses(currentStudent, currentStudent.getPreviousCourses(), true);

        //Frame title + new layout
        setTitle("Student Management System: View Previous Course Details");
        setLayout(new BorderLayout());

        //Title panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); //top, left, bottom, right

        //Sub-texts for the title panel + styling
        JLabel headerLabel1 = new JLabel("Please see below for information about", SwingConstants.CENTER);
        JLabel headerLabel2 = new JLabel("your previous courses, or see further options below.", SwingConstants.CENTER);

        headerLabel1.setFont(new Font("Monospaced", Font.BOLD, 17));
        headerLabel2.setFont(new Font("Monospaced", Font.BOLD, 17));

        headerPanel.add(headerLabel1);
        headerPanel.add(headerLabel2);

        //Adding title panel to upper of frame 
        this.add(headerPanel, BorderLayout.NORTH);

        //Centre panel with course information
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        coursePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //Grabbing the student's previously enrolled courses
        //If no courses in hashmap, appropriate message is displayed
        if (currentStudent.getPreviousCourses().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("> No previous courses exist yet . . .");
            noCoursesLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
            coursePanel.add(noCoursesLabel);

        } else {

            //else, iterate through the enrolled courses hashmap and add information to centre panel
            for (String courseId : currentStudent.getPreviousCourses().keySet()) {

                Course course = allCourses.get(courseId); //get current course from string courseId

                //retrieve student's grade + the lecturer's first and last names based on their id
                Float grade = currentStudent.getPreviousCourses().get(courseId);
                Lecturer lecturer = (Lecturer) lecturerDAO.getById(Integer.parseInt(course.getCourseLecturer()));

                //add all info to the centre panel
                coursePanel.add(Box.createVerticalStrut(10));
                coursePanel.add(makeLabel(course.getCourseId() + ", " + course.getCourseName(), true));
                coursePanel.add(makeLabel("> Your Grade: " + GradesUtil.convertFloatToGrade(grade), false));
                coursePanel.add(makeLabel("> Major: " + course.getCourseMajor(), false));

                String prerequisite = (course.getCoursePrerequisite() == null ? "None" : course.getCoursePrerequisite());

                coursePanel.add(makeLabel("> Prerequisite: " + prerequisite, false));
                coursePanel.add(makeLabel("> Estimated Hours: " + course.getCourseEstimatedHours(), false));
                coursePanel.add(makeLabel("> Lecturer: Dr. " + lecturer.getFirstName() + " " + lecturer.getLastName(), false));
                coursePanel.add(makeLabel("> Description: " + course.getCourseDescription(), false));

                //spacing and line separator
                coursePanel.add(Box.createVerticalStrut(15));
                coursePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            }
        }

        //make the centre panel scrollable both horizontally and vertically
        JScrollPane scrollPane = new JScrollPane(coursePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        //Button setup, config, styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        //Action commands for controller
        backButton.setActionCommand("c");
        exitButton.setActionCommand("x");

        StudentViewCoursesController controller = new StudentViewCoursesController(this, currentStudent);
        JButton[] buttons = {backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            button.addActionListener(controller);
            buttonPanel.add(button);
        }

        //Add button panel to lower part of frame
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /*
    Quick helper method for text labels for the courses display;
    dynamic changing of boldness.
     */
    private JLabel makeLabel(String text, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Monospaced", bold ? Font.BOLD : Font.PLAIN, 16));
        return label;
    }
}