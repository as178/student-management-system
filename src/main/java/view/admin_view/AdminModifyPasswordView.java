/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

import abstract_classes.User;
import abstract_classes.UserViewAndModifyDetailsView;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import concrete_classes.other.ValidationUtil;
import controller.UserController;
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import objects.Admin;
import objects.Lecturer;
import objects.Student;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 */
public class AdminModifyPasswordView extends UserViewAndModifyDetailsView {

    private Admin currentAdmin;

    public AdminModifyPasswordView(User userToModify, Admin currentAdmin) {
        super(userToModify);
        this.currentAdmin = currentAdmin;
    }

    @Override
    protected JPanel buildFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Font labelFont = new Font("Monospaced", Font.BOLD, 16);
        Font labelFont2 = new Font("Monospaced", Font.PLAIN, 14);

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
        panel.add(createLabel(currentUser.getPersonalEmail(), labelFont2));  // as label

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Phone Number:", labelFont));
        panel.add(createHintLabel("e.g. xxx xxx xxxx, +xx xx xxx xxxx, xxxxxxxxxx"));
        panel.add(createLabel(currentUser.getPhoneNumber(), labelFont2));  // as label

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Address:", labelFont));
        panel.add(createHintLabel("e.g. 123 Street Name Suburb City 1234. No commas allowed."));
        panel.add(createLabel(currentUser.getAddress(), labelFont2));  // as label

        panel.add(Box.createVerticalStrut(10));
        return panel;
    }

    @Override
    protected void handleSave() {
        String newPassword = new String(passwordField.getPassword()).trim();

        if (!ValidationUtil.checkPassword(newPassword)) {
            PopUpUtil.displayError("Passwords must be 8 to 30 characters,\nplease try again.");
            return;
        }

        currentUser.setPassword(newPassword);

        if (currentUser instanceof Student) {
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.update(currentUser);
            UserController.setCurrentUsers(studentDAO.getAllUsers());

        } else if (currentUser instanceof Lecturer) {
            LecturerDAO lecturerDAO = new LecturerDAO();
            lecturerDAO.update(currentUser);
            UserController.setCurrentUsers(lecturerDAO.getAllUsers());

        }
    }

    @Override
    protected void handleBack() {
        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to go back?\nYour changes won't be saved.",
                "Go Back Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            NavigationUtil.newFrame(new AdminDashboardView(currentAdmin));
        }
    }
}