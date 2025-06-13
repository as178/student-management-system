/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.lecturer_view;

import abstract_classes.AbstractFormView;
import controller.UserController;
import objects.Lecturer;
import objects.Course;
import utility_classes.NavigationUtil;
import utility_classes.PopUpUtil;
import utility_classes.ValidationUtil;
import dao.CourseDAO;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a Lecturer wishes to update course information of
 * the courses they teach. This class extends the AbstractFormView, which
 * handles a lot of GUI logic behind the scenes, making the setup more
 * efficient.
 *
 */
public class LecturerEditCoursesView extends AbstractFormView<Course> { //Course is the type of object we're dealing with

    private JTextField courseName, courseEstimatedHours, courseDescription;

    public LecturerEditCoursesView(Course course) {
        super(course,
                "Student Management System: Edit Course Information",
                "Edit Information for " + course.getCourseId());
    }

    /*
    Method to configure the form panel.
     */
    @Override
    protected JPanel buildFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //laying out components top to bottom

        Font labelFont = new Font("Monospaced", Font.BOLD, 16); //font for smaller headers
        Font labelFont2 = new Font("Monospaced", Font.PLAIN, 14); //font for information

        panel.add(Box.createVerticalStrut(10)); //spacings inserted for nicer visuals
        panel.add(createLabel("Course ID:", labelFont));
        panel.add(createLabel(currentObject.getCourseId(), labelFont2)); //currentObject = chosen course

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Lecturer ID:", labelFont));
        panel.add(createLabel((currentObject.getCourseLecturer() == null) ? " / " : currentObject.getCourseLecturer(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Major:", labelFont));
        panel.add(createLabel(currentObject.getCourseMajor(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Prerequisite:", labelFont));
        String prerequisite = (currentObject.getCoursePrerequisite() == null ? "None" : currentObject.getCoursePrerequisite());
        panel.add(createLabel(prerequisite, labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Course Name:", labelFont));
        panel.add(createHintLabel("Must be 4 - 46 characters."));
        courseName = new JTextField(currentObject.getCourseName());
        courseName.setFont(labelFont2);
        panel.add(courseName);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Estimated Hours:", labelFont));
        panel.add(createHintLabel("Must be 1 - 150 hours."));
        courseEstimatedHours = new JTextField("" + currentObject.getCourseEstimatedHours());
        courseEstimatedHours.setFont(labelFont2);
        panel.add(courseEstimatedHours);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Course Description:", labelFont));
        panel.add(createHintLabel("Must be 5 - 70 characters."));
        courseDescription = new JTextField(currentObject.getCourseDescription());
        courseDescription.setFont(labelFont2);
        panel.add(courseDescription);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    /*
    Overriden method for handling going back to
    the previous dashboard.
     */
    @Override
    protected void handleBack() {
        int confirmation = PopUpUtil.displayConfirmInfo(
                "Are you sure you want to go back?\nYour changes won't be saved.");

        if (confirmation == JOptionPane.YES_OPTION) {
            NavigationUtil.newFrame(new LecturerManageCoursesView((Lecturer) UserController.getCurrentUser()));
        }
    }

    /*
    This method takes the input from all the text fields and checks
    them using the ValidationUtil (established rules and restrictions
    for the information). If a restriction is breached the user is 
    alerted, otherwise Course information is saved in memory and reflected
    within the database.
     */
    @Override
    protected void handleSave() {
        //getting the information from the textfields
        String newName = courseName.getText().trim();
        String newEstimatedHours = courseEstimatedHours.getText().trim();
        String newDescription = courseDescription.getText().trim();

        //checks using validation util methods
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

        //updating the course in memory
        currentObject.setCourseName(newName);
        currentObject.setCourseEstimatedHours(Integer.parseInt(newEstimatedHours));
        currentObject.setCourseDescription(newDescription);

        //updating the Course table
        CourseDAO courseDAO = new CourseDAO();
        courseDAO.updateCourse(currentObject);
    }
}
