/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

import utility_classes.PopUpUtil;
import utility_classes.ValidationUtil;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a User wishes to view and/or modify their personal
 * information. The GUI and logic is merged into one class for ease of
 * understanding and general use case. This abstract class extends the general
 * AbstractFormView, and is then extended by all user types having the ability
 * to view/modify personal information.
 *
 */
public abstract class UserViewAndModifyDetailsView extends AbstractFormView<User> { //User is the type of object we're dealing with

    protected JPasswordField passwordField; //special text field to hide password being typed in
    protected JTextField emailField, phoneField, addressField;

    public UserViewAndModifyDetailsView(User currentUser) {
        super(currentUser,
                "Student Management System: View/Modify Your Details",
                "View/Modify your Information");
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
        panel.add(createLabel("Full Name:", labelFont));
        panel.add(createLabel(currentObject.getFirstName() + " " + currentObject.getLastName(), labelFont2)); //currentObject = logged in user

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
        panel.add(createHintLabel("e.g. user@example.com"));
        emailField = new JTextField(currentObject.getPersonalEmail());
        emailField.setFont(labelFont2);
        panel.add(emailField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Phone Number:", labelFont));
        panel.add(createHintLabel("e.g. xxx xxx xxxx, +xx xx xxx xxxx, xxxxxxxxxx"));
        phoneField = new JTextField(currentObject.getPhoneNumber());
        phoneField.setFont(labelFont2);
        panel.add(phoneField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Address:", labelFont));
        panel.add(createHintLabel("e.g. 123 Street Name Suburb City 1234. No commas allowed."));
        addressField = new JTextField(currentObject.getAddress());
        addressField.setFont(labelFont2);
        panel.add(addressField);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    /*
    This method takes the input from all the text fields and checks
    them using the ValidationUtil (established rules and restrictions
    for the information). If a restriction is breached the user is 
    alerted, otherwise their information is saved in memory and in
    the database.
     */
    @Override
    protected void handleSave() {
        String newPassword = new String(passwordField.getPassword()).trim();
        String newEmail = emailField.getText().trim();
        String newPhone = phoneField.getText().trim();
        String newAddress = addressField.getText().trim();

        if (!ValidationUtil.checkIntegerRange(newPassword.length(), 8, 30)) {
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

        currentObject.setPassword(newPassword);
        currentObject.setPersonalEmail(newEmail);
        currentObject.setPhoneNumber(ValidationUtil.formatPhoneNumber(newPhone));
        currentObject.setAddress(newAddress);

        //save user via method overriden by every user type
        currentObject.saveCurrrentUser();
    }

    /*
    Overriden method for handling going back to the logged
    in user's main previous dashboard depending on the
    type of user they are.
     */
    @Override
    protected void handleBack() {
        int confirmation = PopUpUtil.displayConfirmInfo(
                "Are you sure you want to go back?\nYour changes won't be saved."); //confirmation to alert user

        if (confirmation == JOptionPane.YES_OPTION) {
            currentObject.userMainDashboard();
        }
    }
}
