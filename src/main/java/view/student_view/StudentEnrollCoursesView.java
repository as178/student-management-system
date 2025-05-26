/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.student_view;

import objects.Course;
import objects.Student;
import controller.student_controllers.StudentModifyCoursesController;
import dao.CourseDAO;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays all of the courses from the student's major and allows
 * them to enroll into the ones they are eligible for.
 *
 * Controller: StudentModifyCoursesController
 *
 */
public class StudentEnrollCoursesView extends JFrame {

    private StudentModifyCoursesController controller;
    private Student currentStudent;

    private CourseDAO courseDAO = new CourseDAO();
    private HashMap<String, Course> allCourses; //all courses
    private HashMap<String, Course> allMajorCourses = new HashMap<>(); //student's major courses
    private HashMap<String, Course> availableCourses = new HashMap<>(); //courses the student is eligible for

    public StudentEnrollCoursesView(Student currentStudent) {
        this.currentStudent = currentStudent;
        this.controller = new StudentModifyCoursesController(this, currentStudent);
        this.allCourses = courseDAO.getAllCourses();

        setTitle("Student Management System: Enroll into Courses");
        setLayout(new BorderLayout());

        //Title panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //Sub-texts for title panel
        JLabel headerLabel1 = new JLabel("All Your Major's Courses ---> Their Prerequisites", SwingConstants.CENTER);
        JLabel headerLabel2 = new JLabel("You may enroll into courses you are eligible for.", SwingConstants.CENTER);

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

        //Load up all courses in student's major
        this.allMajorCourses = controller.getMajorCourses(this.allMajorCourses, this.allCourses);
        
        //Obtain which courses student is eligible to take
        controller.getAvailableCourses(this.availableCourses, this.allCourses);

        //Iterate through all major courses
        for (Course course : allMajorCourses.values()) {
            
            //align rows to the left
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

            row.add(Box.createHorizontalStrut(40)); //spacing

            //if this course is an eligible course for the student 
            if (availableCourses.containsKey(course.getCourseId())) {
                
                //make an enroll button next to the course id
                JButton enrollButton = new JButton("Enroll");
                enrollButton.setFont(new Font("Monospaced", Font.BOLD, 14));
                enrollButton.setActionCommand("e," + course.getCourseId()); //for controller to handle 
                enrollButton.addActionListener(controller);
                
                row.add(enrollButton); //add the button to the row 
            } else {
                row.add(Box.createHorizontalStrut(85)); //otherwise add spacing 
            }
            
            //text formatting for displaying course id and names, as well as prerequisites
            String prerequisite = (course.getCoursePrerequisite() == null ? "  None" : course.getCoursePrerequisite());
            JLabel courseLabel = new JLabel("  >  " + course.getCourseId() + "   --->   " + prerequisite);
            courseLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
            courseLabel.setPreferredSize(new Dimension(400, 25)); // Fixed width label

            //Adding the formatted text labels to the row ==> course panel + spacing 
            row.add(courseLabel);
            coursePanel.add(row);
            coursePanel.add(Box.createVerticalStrut(10));
        }

        //Making course panel scrollable + add to frame
        JScrollPane scrollPane = new JScrollPane(coursePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);

        //Buttons config + styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        backButton.setActionCommand("b2");
        exitButton.setActionCommand("x");

        JButton[] buttons = {backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            button.addActionListener(controller);
            buttonPanel.add(button);
        }

        //Add button panel to frame
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
