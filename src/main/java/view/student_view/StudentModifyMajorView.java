/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.student_view;

import controller.student_controllers.StudentModifyMajorController;
import objects.Student;
import dao.MajorDAO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class enables the student to change their major based on a dynamic list
 * of available majors (which excludes the major the student is currently
 * taking).
 * 
 * Controller: StudentModifyMajorController
 *
 */
public class StudentModifyMajorView extends JFrame {

    private Student currentStudent;
    private ArrayList<String> availableMajors; //all majors except the student's

    //Majors loaded up in an arraylist for convenience
    private MajorDAO majorDAO = new MajorDAO();
    private ArrayList<String> allMajors = majorDAO.readAllMajors();
    
    //used with the JRadioButtons to enable single major selection at a time
    private ButtonGroup majorsButtonGroup;
    
    private JPanel centrePanel; 
    private StudentModifyMajorController controller;

    public StudentModifyMajorView(Student currentStudent) {
        this.currentStudent = currentStudent;
        this.availableMajors = new ArrayList<String>();

        setTitle("Student Management System: Modify Major");

        getAvailableMajors();
        controller = new StudentModifyMajorController(this, currentStudent, availableMajors);
        buildGUI();
    }

    /*
    Helper method to iterate through all majors and
    add to the arraylist the ones the student isn't 
    taking.
    */
    private void getAvailableMajors() {

        this.availableMajors.clear(); //clear list to keep it updated properly

        for (String major : allMajors) {
            if (!currentStudent.getMajor().equals(major)) {
                this.availableMajors.add(major);
            }
        }
    }

    /*
    Method to build the GUI for this class.
    */
    private void buildGUI() {
        //Main panel config
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        //Title label
        JLabel headerLabel = new JLabel("Please choose your new Major below:", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        //Init centre panel + button group
        centrePanel = new JPanel(new GridLayout(0, 1, 10, 8));
        majorsButtonGroup = new ButtonGroup();

        //iterate through list of available majors and
        //make them each a JRadioButton that the user
        //can select, add to centre panel
        for (String major : availableMajors) {
            JRadioButton radioButton = new JRadioButton(major);
            radioButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
            majorsButtonGroup.add(radioButton);
            centrePanel.add(radioButton);
        }

        //adding centre panel to main panel
        mainPanel.add(centrePanel, BorderLayout.CENTER);

        //Button config + styling 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton confirmButton = new JButton("Confirm Change");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        confirmButton.setActionCommand("c");
        backButton.setActionCommand("b");
        exitButton.setActionCommand("x");

        JButton[] buttons = {confirmButton, backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            button.addActionListener(controller);
            buttonPanel.add(button);
        }

        //Adding button panel to main panel + child components inserted
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /*
    Helper method for controller to determine
    which major the student wishes to switch to.
    */
    public String getSelectedMajor() {

        //iterate through all components in centre panel
        for (Component component : centrePanel.getComponents()) {

            //if component is an instance of JRadioButton
            if (component instanceof JRadioButton) {

                JRadioButton radio = (JRadioButton) component;

                //return the button's text
                if (radio.isSelected()) {
                    return radio.getText();
                }
            }
        }
        return null; //else return null
    }
}
