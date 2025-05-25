/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

import concrete_classes.admin.Admin;
import concrete_classes.lecturer.Lecturer;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import concrete_classes.other.ValidationUtil;
import concrete_classes.student.Student;
import controller.UserController;
import dao.AdminDAO;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import view.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a User wishes to view and/or modify their personal
 * information.
 */
public abstract class UserViewAndModifyDetailsView extends JFrame {

    private User currentUser;
    private JPasswordField passwordField;
    private JTextField emailField, phoneField, addressField;

    public UserViewAndModifyDetailsView(User currentUser) {

        this.currentUser = currentUser;
        setTitle("Student Management System: User View/Modify Details");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel headerLabel = new JLabel("View/Modify your Information", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 21));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = buildFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = buildButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font labelFont2 = new Font("SansSerif", Font.PLAIN, 14);

        panel.add(Box.createVerticalStrut(10));
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

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JLabel createHintLabel(String text) {
        JLabel hint = new JLabel(text);
        hint.setFont(new Font("SansSerif", Font.ITALIC, 13));
        hint.setForeground(Color.GRAY);
        return hint;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton saveButton = new JButton("Save Changes");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to go back?\nYour changes won't be saved.",
                        "Go Back Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    if (currentUser instanceof Student) {
                        NavigationUtil.newFrame(new StudentDashboardView((Student) currentUser));
                    } else if (currentUser instanceof Lecturer) {
                        //NavigationUtil.newFrame(new LecturerDashboardView((Lecturer) currentUser));
                    } else if (currentUser instanceof Admin) {
                        NavigationUtil.newFrame(new AdminDashboardView((Admin) currentUser));
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationUtil.exitProgram();
            }
        });

        panel.add(saveButton);
        panel.add(backButton);
        panel.add(exitButton);

        return panel;
    }

    private void handleSave() {
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
}
