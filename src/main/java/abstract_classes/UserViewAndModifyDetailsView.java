/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import concrete_classes.other.ValidationUtil;
import controller.UserController;
import dao.AdminDAO;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import objects.Admin;
import objects.Lecturer;
import objects.Student;
import view.admin_view.AdminDashboardView;
import view.lecturer_view.LecturerDashboardView;
import view.student_view.StudentDashboardView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a User wishes to view and/or modify their personal
 * information. The GUI and logic is merged into one class for easy of
 * understanding and general use case.
 *
 */
public abstract class UserViewAndModifyDetailsView extends JFrame {

    public User currentUser;
    public JPasswordField passwordField; //special text field to hide password being typed in
    protected JTextField emailField, phoneField, addressField;

    public UserViewAndModifyDetailsView(User currentUser) {

        this.currentUser = currentUser;
        this.setTitle("Student Management System: View/Modify Your Details");

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        //Header title
        JLabel headerLabel = new JLabel("View/Modify your Information", SwingConstants.CENTER);
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
    protected JPanel buildFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //laying out components top to bottom

        Font labelFont = new Font("Monospaced", Font.BOLD, 16); //font for smaller headers
        Font labelFont2 = new Font("Monospaced", Font.PLAIN, 14); //font for information

        panel.add(Box.createVerticalStrut(10)); //spacings inserted for nicer visuals
        panel.add(createLabel("Full Name:", labelFont));
        panel.add(createLabel(currentUser.getFirstName() + " " + currentUser.getLastName(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Date of Birth:", labelFont));
        panel.add(createLabel(currentUser.getDateOfBirth(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Password:", labelFont));
        passwordField = new JPasswordField(currentUser.getPassword());
        passwordField.setFont(labelFont2);
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("University Email:", labelFont));
        panel.add(createLabel(currentUser.getUniEmail(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Email:", labelFont));
        panel.add(createHintLabel("e.g. user@example.com"));
        emailField = new JTextField(currentUser.getPersonalEmail());
        emailField.setFont(labelFont2);
        panel.add(emailField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Phone Number:", labelFont));
        panel.add(createHintLabel("e.g. xxx xxx xxxx, +xx xx xxx xxxx, xxxxxxxxxx"));
        phoneField = new JTextField(currentUser.getPhoneNumber());
        phoneField.setFont(labelFont2);
        panel.add(phoneField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Address:", labelFont));
        panel.add(createHintLabel("e.g. 123 Street Name Suburb City 1234. No commas allowed."));
        addressField = new JTextField(currentUser.getAddress());
        addressField.setFont(labelFont2);
        panel.add(addressField);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    /*
    Small helper method for making quick labels with a specified font.
     */
    protected JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    /*
    Small helper method for making quick labels for providing the
    user with formatting hints for editable fields.
     */
    protected JLabel createHintLabel(String text) {
        JLabel hint = new JLabel(text);
        hint.setFont(new Font("Monospaced", Font.ITALIC, 13));
        hint.setForeground(Color.GRAY);
        return hint;
    }

    /*
    Method for configuring the button panel.
     */
    protected JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); //each row to be centered with custom gaps

        JButton saveButton = new JButton("Save Changes");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        /*
        Adding action listeners for buttons:
        
        - "Save Changes" calls on the handleSave() method
            to handling saving of user inputs from the
            text fields to the correct tables in the
            database.
        
        - "Back" lets the user know their changes won't
            be saved and takes them back to their
            respective dashboard depending on the type
            of user they are.
        
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
                handleBack();
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
    alerted, otherwise their information is saved in memory and in
    the database.
     */
    protected void handleSave() {
        String newPassword = new String(passwordField.getPassword()).trim();
        String newEmail = emailField.getText().trim();
        String newPhone = phoneField.getText().trim();
        String newAddress = addressField.getText().trim();

        if (!ValidationUtil.checkPassword(newPassword)) {
            PopUpUtil.displayError("Passwords must be 8 to 30 characters,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkEmail(newEmail)) {
            PopUpUtil.displayError("Invalid email address,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkPhoneNumber(newPhone)) {
            PopUpUtil.displayError("Invalid phone number,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkAddress(newAddress)) {
            PopUpUtil.displayError("Invalid address format,\nplease try again.");
            return;
        }

        currentUser.setPassword(newPassword);
        currentUser.setPersonalEmail(newEmail);
        currentUser.setPhoneNumber(ValidationUtil.formatPhoneNumber(newPhone));
        currentUser.setAddress(newAddress);

        //Call appropriate DAO based on user type and save user
        if (currentUser instanceof Student) {
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.update(currentUser);
            UserController.setCurrentUsers(studentDAO.getAllUsers());

        } else if (currentUser instanceof Lecturer) {
            LecturerDAO lecturerDAO = new LecturerDAO();
            lecturerDAO.update(currentUser);
            UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        } else if (currentUser instanceof Admin) {
            AdminDAO adminDAO = new AdminDAO();
            adminDAO.update(currentUser);
            UserController.setCurrentUsers(adminDAO.getAllUsers());
        }
    }

    protected void handleBack() {
        int confirmation = PopUpUtil.displayConfirmInfo(
                "Are you sure you want to go back?\nYour changes won't be saved.");

        if (confirmation == JOptionPane.YES_OPTION) {
            if (currentUser instanceof Student) {
                NavigationUtil.newFrame(new StudentDashboardView((Student) currentUser));
            } else if (currentUser instanceof Lecturer) {
                NavigationUtil.newFrame(new LecturerDashboardView((Lecturer) currentUser));
            } else if (currentUser instanceof Admin) {
                NavigationUtil.newFrame(new AdminDashboardView((Admin) currentUser));
            }
        }
    }
}
