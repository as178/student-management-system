/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.lecturer_view;

import controller.lecturer_controllers.LecturerCourseStudentsListController;
import dao.CourseDAO;
import dao.StudentDAO;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import objects.Student;
import objects.Course;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class displays the courses the lecturer user is teaching; further
 * options are provided for editing the course information and listing students
 * within a chosen course (grades configuration).
 * 
 * Controller: LecturerCourseStudentsListController
 *
 */
public class LecturerCourseStudentsListView extends JFrame {

    private Course currentCourse;
    private HashMap<Integer, String> studentGrades;
    private LecturerCourseStudentsListController controller;

    public LecturerCourseStudentsListView(Course currentCourse) {
        this.currentCourse = currentCourse;

        CourseDAO courseDAO = new CourseDAO();
        this.studentGrades = courseDAO.readEnrolledStudentsGrades(currentCourse.getCourseId());

        this.controller = new LecturerCourseStudentsListController(this, currentCourse, studentGrades);
        setTitle("Enrolled Students for " + currentCourse.getCourseName());
        buildGUI();
    }

    private void buildGUI() {

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //Header title
        JLabel headerLabel = new JLabel("Enrolled Students for " + currentCourse.getCourseId(), SwingConstants.CENTER);
        headerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        //List panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        //Utilising student DAO to retrieve student objects
        StudentDAO studentDAO = new StudentDAO();

        //Getting all students in chosen course and inputting their information
        //to the student panel for styling and config ==> list panel
        for (HashMap.Entry<Integer, String> entry : studentGrades.entrySet()) {

            Integer studentId = entry.getKey();
            String grade = entry.getValue();
            String formattedGrade;

            //formatting for the grades to maintain them at 2 d.p.
            if (grade == null || grade.isEmpty()) {
                formattedGrade = "";
            } else {
                try {
                    double floatGrade = Double.parseDouble(grade);
                    formattedGrade = String.format("%.2f", floatGrade);
                } catch (NumberFormatException e) {
                    formattedGrade = grade;
                }
            }

            Student student = (Student) studentDAO.getById(studentId);

            JPanel studentPanel = studentListRowPanel(student, formattedGrade);
            listPanel.add(studentPanel);
            listPanel.add(Box.createVerticalStrut(10)); //spacing
        }

        //Make list panel scrollable + add to main panel
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //Buttons panel, buttons config + styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

        //Add button panel to main panel + inserting children components
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.setContentPane(mainPanel);
    }

    /*
    Helper method to return rows for students within the course.
     */
    public JPanel studentListRowPanel(Student student, String currentGrade) {

        JPanel panel = new JPanel();
        JTextField gradeField;
        JButton setGradeButton, signOffButton;

        //Setting out layout with fixed size
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setMaximumSize(new Dimension(660, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        //Student label, id + full name + grade label
        JLabel studentLabel = new JLabel(student.getId() + ", " + student.getFirstName() + " " + student.getLastName());
        studentLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        studentLabel.setPreferredSize(new Dimension(220, 30));
        studentLabel.setMaximumSize(new Dimension(220, 30));

        JLabel gradeLabel = new JLabel("Current Grade:");
        gradeLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gradeLabel.setMaximumSize(new Dimension(130, 30));

        //Init grade field + styling
        gradeField = new JTextField(currentGrade, 6);
        gradeField.setHorizontalAlignment(JTextField.CENTER);
        gradeField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gradeField.setEditable(false);
        gradeField.setMaximumSize(new Dimension(60, 30));

        //Add fields and labels to panel to be returned 
        panel.add(studentLabel);
        panel.add(gradeLabel);
        panel.add(gradeField);

        //Buttons + config
        setGradeButton = new JButton("Set Grade");
        signOffButton = new JButton("Sign Off");

        //Action commands with student id to be handled by controller
        setGradeButton.setActionCommand("g," + student.getId());
        signOffButton.setActionCommand("s," + student.getId());

        JButton[] buttons = {setGradeButton, signOffButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            button.addActionListener(controller);
            button.setMaximumSize(new Dimension(110, 30));
            panel.add(Box.createHorizontalStrut(10));
            panel.add(button);
        }

        //return row
        return panel;
    }
}
