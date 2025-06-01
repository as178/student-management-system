/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

import abstract_classes.User;
import concrete_classes.other.NavigationUtil;
import concrete_classes.other.PopUpUtil;
import concrete_classes.other.ValidationUtil;
import controller.UserController;
import java.awt.Font;
import javax.swing.*;
import objects.Admin;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when an Admin wishes to view any Student/Lecturer's
 * information, as well as edit that specific user's password. This class
 * extends the AbstractFormView, which handles a lot of GUI logic behind the
 * scenes, making the setup more efficient.
 *
 */
public class AdminModifyPasswordView extends AbstractFormView<User> { //User is the type of object we're dealing with

    private JPasswordField passwordField;

    public AdminModifyPasswordView(User userToModify) {
        super(userToModify,
                "Student Management System: Modify a User's Password",
                "Modify User's Password");
    }

    @Override
    protected JPanel buildFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //laying out components top to bottom
        Font labelFont = new Font("Monospaced", Font.BOLD, 16); //font for smaller headers
        Font labelFont2 = new Font("Monospaced", Font.PLAIN, 14); //font for information

        panel.add(Box.createVerticalStrut(10)); //spacings inserted for nicer visuals
        panel.add(createLabel("Full Name:", labelFont));
        panel.add(createLabel(currentObject.getFirstName() + " " + currentObject.getLastName(), labelFont2)); //currentObject = userToModify

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Date of Birth:", labelFont));
        panel.add(createLabel(currentObject.getDateOfBirth(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Password:", labelFont));
        passwordField = new JPasswordField(currentObject.getPassword());
        passwordField.setFont(labelFont2);
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("University Email:", labelFont));
        panel.add(createLabel(currentObject.getUniEmail(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Email:", labelFont));
        panel.add(createLabel(currentObject.getPersonalEmail(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Phone Number:", labelFont));
        panel.add(createLabel(currentObject.getPhoneNumber(), labelFont2));

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Address:", labelFont));
        panel.add(createLabel(currentObject.getAddress(), labelFont2));

        panel.add(Box.createVerticalStrut(10));
        return panel;
    }

    /*
    This method takes the input from the password text field and checks
    it using the ValidationUtil (established rules and restrictions
    for information). If a restriction is breached admin is 
    alerted, otherwise the user's new password is saved in memory and in
    the database.
     */
    @Override
    protected void handleSave() {
        String newPassword = new String(passwordField.getPassword()).trim();

        if (!ValidationUtil.checkPassword(newPassword)) {
            PopUpUtil.displayError("Passwords must be 8 to 30 characters,\nplease try again.");
            return;
        }

        currentObject.setPassword(newPassword);
        currentObject.saveCurrrentUser();
    }

    /*
    Overriden method for handling going back to the
    lookup dashboard.
     */
    @Override
    protected void handleBack() {
        int confirmation = PopUpUtil.displayConfirmInfo(
                "Are you sure you want to go back?\nYour changes won't be saved."); //confirmation to alert admin

        if (confirmation == JOptionPane.YES_OPTION) {
            NavigationUtil.newFrame(new AdminLoadUserView((Admin) UserController.getCurrentUser()));
        }
    }
}
