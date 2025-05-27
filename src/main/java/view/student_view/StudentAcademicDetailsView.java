/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.student_view;

import concrete_classes.other.GradesUtil;
import objects.Student;
import controller.student_controllers.StudentAcademicDetailsController;
import dao.CourseDAO;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This view displays the student's academic information at a glance, this
 * includes their GPA, major, current courses and grades. Further options are
 * also displayed.
 *
 * Controller: StudentAcademicDetailsController
 *
 *
 */
public class StudentAcademicDetailsView extends JFrame {

    private Student currentStudent;

    public StudentAcademicDetailsView(Student currentStudent) {
        this.currentStudent = currentStudent;

        setTitle("Student Management System: View Your Academic Details");

        //Main panel initial setup
        JPanel mainPanel = new JPanel(new BorderLayout(15, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        //Title panel
        JLabel headerLabel = new JLabel("Your Academic Details: Summary", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        //Centre panel
        JPanel centrePanel = buildAcademicPanel();
        mainPanel.add(centrePanel, BorderLayout.CENTER);

        //Button panel
        JPanel buttonPanel = buildButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        //Child components inserted
        this.setContentPane(mainPanel);
    }

    private JPanel buildAcademicPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Font labelFont = new Font("Monospaced", Font.PLAIN, 18);

        //Load up student's course hashmaps
        CourseDAO courseDAO = new CourseDAO();
        courseDAO.readStudentsCourses(currentStudent, currentStudent.getEnrolledCourses(), false);
        courseDAO.readStudentsCourses(currentStudent, currentStudent.getPreviousCourses(), true);

        //Display GPA
        JLabel gpaLabel = new JLabel("GPA: " + String.format("%.2f", currentStudent.getGPA()));
        gpaLabel.setFont(labelFont);
        panel.add(gpaLabel);
        panel.add(new JLabel(" ")); //spacing

        //Display major 
        JLabel majorLabel = new JLabel("Major: " + currentStudent.getMajor());
        majorLabel.setFont(labelFont);
        panel.add(majorLabel);
        panel.add(new JLabel(" ")); //spacing

        //Birefly display current courses and grades at a glance 
        JLabel coursesLabel = new JLabel("Current Courses & Grades (Maximum of 8):");
        coursesLabel.setFont(labelFont);
        panel.add(coursesLabel);

        //If no courses are being taken, appropriate message is displayed
        if (currentStudent.getEnrolledCourses().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("> No courses being taken at this time . . .");
            noCoursesLabel.setFont(labelFont);
            panel.add(noCoursesLabel);

        } else {

            //else currently enrolled courses are iterated through and shown in a nice format
            //first line then iteration 
            JLabel firstLine = new JLabel("[ Course Code || Grade ]");
            firstLine.setFont(labelFont);
            panel.add(firstLine);

            //current courses and student's grades for each
            for (HashMap.Entry<String, Float> entry : currentStudent.getEnrolledCourses().entrySet()) {
                String line = ">   " + entry.getKey() + "   ||  " + GradesUtil.convertFloatToGrade(entry.getValue());
                JLabel courseLabel = new JLabel(line);
                courseLabel.setFont(labelFont);
                panel.add(courseLabel);
            }
        }

        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Font buttonFont = new Font("Monospaced", Font.BOLD, 14);

        //First row of buttons
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton viewCourseDetailsButton = new JButton("View Course Details");
        JButton changeMajorButton = new JButton("Change Major");
        JButton changeCoursesButton = new JButton("Change Courses");
        row1.add(viewCourseDetailsButton);
        row1.add(changeMajorButton);
        row1.add(changeCoursesButton);

        //Second row of buttons
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");
        row2.add(backButton);
        row2.add(exitButton);

        //Buttons config + styling
        StudentAcademicDetailsController controller = new StudentAcademicDetailsController(this, currentStudent);
        JButton[] buttons = {viewCourseDetailsButton, changeMajorButton, changeCoursesButton, backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.addActionListener(controller);
        }

        //Adding both rows to panel to be returned
        panel.add(row1);
        panel.add(row2);

        viewCourseDetailsButton.setActionCommand("1");
        changeMajorButton.setActionCommand("2");
        changeCoursesButton.setActionCommand("3");
        backButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        return panel;
    }
}
