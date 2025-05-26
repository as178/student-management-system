/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.lecturer_view;

import objects.Lecturer;
import objects.Course;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import concrete_classes.other.ValidationUtil;
import dao.CourseDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a Lecturer wishes to update course information of
 * the courses they teach.
 *
 */
public class LecturerEditCoursesView extends JFrame {

    private Lecturer currentLecturer;
    private Course course;
    private JTextField courseName, courseEstimatedHours, courseDescription;

    public LecturerEditCoursesView(Lecturer currentLecturer, Course course) {

        this.currentLecturer = currentLecturer;
        this.course = course;
        this.setTitle("Student Management System: Edit Course Information");

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        //Header title
        JLabel headerLabel = new JLabel("Edit Information for " + course.getCourseId(), SwingConstants.CENTER);
        headerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        //Wrapper panel for header title, lower padding ==> main panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        //Scrollable panel where all the information is presented to the user, added to main panel
        JPanel formPanel = buildFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //Buttons at the bottom of frame
        JPanel buttonPanel = buildButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        //Child components inserted
        this.setContentPane(mainPanel);
    }

    /*
    Method to configure the form panel.
     */
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //laying out components top to bottom

        Font labelFont = new Font("Monospaced", Font.BOLD, 16); //font for smaller headers
        Font labelFont2 = new Font("Monospaced", Font.PLAIN, 14); //font for information

        panel.add(Box.createVerticalStrut(10)); //spacings inserted for nicer visuals
        panel.add(createLabel("Course ID:", labelFont));
        panel.add(createLabel(course.getCourseId(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Lecturer ID:", labelFont));
        panel.add(createLabel(course.getCourseLecturer(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Major:", labelFont));
        panel.add(createLabel(course.getCourseMajor(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Prerequisite:", labelFont));
        String prerequisite = (course.getCoursePrerequisite() == null ? "None" : course.getCoursePrerequisite());
        panel.add(createLabel(prerequisite, labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Course Name:", labelFont));
        panel.add(createHintLabel("Must be 4 - 46 characters."));
        courseName = new JTextField(course.getCourseName());
        courseName.setFont(labelFont2);
        panel.add(courseName);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Estimated Hours:", labelFont));
        panel.add(createHintLabel("Must be 1 - 150 hours."));
        courseEstimatedHours = new JTextField("" + course.getCourseEstimatedHours());
        courseEstimatedHours.setFont(labelFont2);
        panel.add(courseEstimatedHours);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Course Description:", labelFont));
        panel.add(createHintLabel("Must be 5 - 70 characters."));
        courseDescription = new JTextField(course.getCourseDescription());
        courseDescription.setFont(labelFont2);
        panel.add(courseDescription);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    /*
    Small helper method for making quick labels with a specified font.
     */
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    /*
    Small helper method for making quick labels for providing the
    user with formatting hints for editable fields.
     */
    private JLabel createHintLabel(String text) {
        JLabel hint = new JLabel(text);
        hint.setFont(new Font("Monospaced", Font.ITALIC, 13));
        hint.setForeground(Color.GRAY);
        return hint;
    }

    /*
    Method for configuring the button panel.
     */
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); //each row to be centered with custom gaps

        JButton saveButton = new JButton("Save Changes");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        /*
        Adding action listeners for buttons:
        
        - "Save Changes" calls on the handleSave() method
            to handling saving of user inputs from the
            text fields to the Course table.
        
        - "Back" lets the user know their changes won't
            be saved and takes them back to the LecturerManageCoursesView.
        
        - "Exit" calls on the NavigationUtil.exitProgram();
            and confirms the user's choice before
            quitting the program.
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = PopUpUtil.displayConfirmInfo(
                        "Are you sure you want to go back?\nYour changes won't be saved.");

                if (confirmation == JOptionPane.YES_OPTION) {
                    NavigationUtil.newFrame(new LecturerManageCoursesView(currentLecturer));
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationUtil.exitProgram();
            }
        });

        //Quick array and for loop for final config and adding buttons to the panel
        JButton[] buttons = {saveButton, backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            panel.add(button);
        }

        return panel;
    }

    /*
    This method takes the input from all the text fields and checks
    them using the ValidationUtil (established rules and restrictions
    for the information). If a restriction is breached the user is 
    alerted, otherwise Course information is saved in memory and reflected
    within the database.
     */
    private void handleSave() {
        String newName = courseName.getText().trim();
        String newEstimatedHours = courseEstimatedHours.getText().trim();
        String newDescription = courseDescription.getText().trim();

        if (!ValidationUtil.checkIntegerRange(newName.length(), 4, 46)) {
            PopUpUtil.displayError("Course name has to be between\n4 to 46 characters, please try again.");
            return;
        }

        if (!ValidationUtil.checkIntegerRange(newEstimatedHours, 1, 150)) {
            PopUpUtil.displayError("Estimated hours must be between 1 and 150.");
            return;
        }

        if (!ValidationUtil.checkIntegerRange(newDescription.length(), 5, 70)) {
            PopUpUtil.displayError("Course description must be\nbetween 5 and 70 characters in length.");
            return;
        }

        course.setCourseName(newName);
        course.setCourseEstimatedHours(Integer.parseInt(newEstimatedHours));
        course.setCourseDescription(newDescription);
        
        CourseDAO courseDAO = new CourseDAO();
        courseDAO.updateCourse(course);
    }
}
