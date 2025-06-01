/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.lecturer_view;

import controller.lecturer_controllers.LecturerManageCoursesController;
import objects.Course;
import objects.Lecturer;
import dao.CourseDAO;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays the courses the lecturer user is teaching; further
 * options are provided for editing the course information and listing students
 * within a chosen course (grades configuration).
 *
 * Controller: LecturerManageCoursesController
 *
 */
public class LecturerManageCoursesView extends JFrame {

    private LecturerManageCoursesController controller;
    private CourseDAO courseDAO;

    public LecturerManageCoursesView(Lecturer currentLecturer) {
        this.courseDAO = new CourseDAO();
        this.controller = new LecturerManageCoursesController(currentLecturer);

        setTitle("Student Management System: Manage Courses");
        setLayout(new BorderLayout());

        //Title panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //Sub-texts for title panel
        JLabel headerLabel1 = new JLabel("Your courses are listed below, please see", SwingConstants.CENTER);
        JLabel headerLabel2 = new JLabel("options listed alongside each course.", SwingConstants.CENTER);

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

        //Load up lecturer's courses hashmap
        this.courseDAO.readLecturerCourses(currentLecturer);

        //If lecturer isn't teaching any courses show appropriate message 
        if (currentLecturer.getCoursesTaught().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("> No assigned courses . . .");
            noCoursesLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
            coursePanel.add(noCoursesLabel);

        } else {
            //else iterate through lecturer's courses and display them
            //each with edit info + list students buttons next to them
            int i = 0;
            for (Course course : currentLecturer.getCoursesTaught().values()) {

                //proper centred alignment
                JPanel fullRowPanel = new JPanel();
                fullRowPanel.setLayout(new BoxLayout(fullRowPanel, BoxLayout.Y_AXIS));
                fullRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //course label to display course id + name
                JLabel courseLabel = new JLabel("> " + course.getCourseId() + ", " + course.getCourseName());
                courseLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
                courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //buttons row
                JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
                buttonRow.setAlignmentX(Component.CENTER_ALIGNMENT);

                //buttons, config + styling
                JButton editCourseButton = new JButton("Edit Course");
                JButton listStudentsButton = new JButton("List Students");

                JButton[] buttons = {editCourseButton, listStudentsButton};
                for (JButton button : buttons) {
                    button.setFont(new Font("Monospaced", Font.BOLD, 14));
                    button.addActionListener(controller);
                }

                //action commands for the controller (includes course ids)
                editCourseButton.setActionCommand("e," + course.getCourseId());
                listStudentsButton.setActionCommand("l," + course.getCourseId());

                //adding buttons to the button row
                buttonRow.add(editCourseButton);
                buttonRow.add(listStudentsButton);

                //adding course label + spacing + buttons row to the full row panel
                fullRowPanel.add(courseLabel);
                fullRowPanel.add(Box.createVerticalStrut(6));
                fullRowPanel.add(buttonRow);

                //wrapper panel for centering text labels on the screen a bit better
                JPanel centreWrapper = new JPanel(new GridBagLayout());
                centreWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
                centreWrapper.add(fullRowPanel);

                //adding wrapper to course panel 
                coursePanel.add(centreWrapper);

                //if its not the last course, print a divider with spacing for aesthetics
                if (i++ < 1) {
                    coursePanel.add(Box.createVerticalStrut(10));
                    coursePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
                    coursePanel.add(Box.createVerticalStrut(10));
                }
            }
        }

        //Make course panel scrollable + add to frame
        JScrollPane scrollPane = new JScrollPane(coursePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);

        //Button config + styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        //Action commands for controller to handle
        backButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        JButton[] buttons = {backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            button.addActionListener(controller);
            buttonPanel.add(button);
        }

        //Adding button panel to the frame
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
