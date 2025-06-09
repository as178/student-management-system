/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

import abstract_classes.AbstractFormView;
import abstract_classes.User;
import controller.UserController;
import dao.MajorDAO;
import java.awt.*;
import javax.swing.*;
import objects.Lecturer;
import objects.objects_interfaces.NewUserInterface;
import objects.Student;
import utility_classes.PopUpUtil;
import utility_classes.ValidationUtil;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when an Admin wishes to register a new user (Student or
 * Lecturer) into the program. This class extends the AbstractFormView, which
 * handles a lot of GUI logic behind the scenes, making the setup more
 * efficient.
 *
 */
public class AdminRegisterNewUserView extends AbstractFormView<User> { //dealing with User as the type

    public AdminRegisterNewUserView(User newUser) {
        super(newUser,
                "Student Management System: Register New User",
                "Please enter the New User's information below:");
    }

    //text fields
    private JTextField firstNameField, lastNameField, dobField, emailField, phoneField, addressField;
    private JPasswordField passwordField;

    //radio buttons for choosing gender
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;

    //choosing major / faculty depending on user 
    private JComboBox<String> majorOrFacultyDropdown;
    private JLabel dynamicLabel;

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
        panel.add(createLabel("First Name:", labelFont));
        firstNameField = new JTextField();
        firstNameField.setFont(labelFont2);
        panel.add(firstNameField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Last Name:", labelFont));
        lastNameField = new JTextField();
        lastNameField.setFont(labelFont2);
        panel.add(lastNameField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Date of Birth:", labelFont));
        panel.add(createHintLabel("i.e. YYYY-MM-DD"));
        dobField = new JTextField();
        dobField.setFont(labelFont2);
        panel.add(dobField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Password:", labelFont));
        passwordField = new JPasswordField();
        passwordField.setFont(labelFont2);
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Email:", labelFont));
        panel.add(createHintLabel("e.g. user@example.com"));
        emailField = new JTextField(currentObject.getPersonalEmail());
        emailField.setFont(labelFont2);
        panel.add(emailField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Phone Number:", labelFont));
        panel.add(createHintLabel("e.g. xxx xxx xxxx, +xx xx xxx xxxx, xxxxxxxxxx"));
        phoneField = new JTextField();
        phoneField.setFont(labelFont2);
        panel.add(phoneField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Address:", labelFont));
        panel.add(createHintLabel("e.g. 123 Street Name Suburb City 1234. No commas allowed."));
        addressField = new JTextField();
        addressField.setFont(labelFont2);
        panel.add(addressField);

        //gender label + JRadio buttons 
        panel.add(Box.createVerticalStrut(10));
        JLabel genderLabel = createLabel("Gender:", labelFont);

        //JRadio buttons + styling
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        maleRadio.setFont(labelFont2);
        femaleRadio.setFont(labelFont2);

        //group the radio buttons for single selection
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        //genderOptions panel + config 
        JPanel genderOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderOptions.add(maleRadio);
        genderOptions.add(femaleRadio);
        panel.add(createFormRow(genderLabel, genderOptions)); //using helper method for alignment

        //choosing major / faculty label based on user type (student / lecturer respectively)
        dynamicLabel = (currentObject instanceof Student) ? createLabel("Major:", labelFont) : createLabel("Faculty:", labelFont);

        //get all majors arraylist from DAO and convert it into a String array
        String[] majors = new MajorDAO().readAllMajors().toArray(new String[0]);

        //set the dropdown box to have majors as options + styling
        majorOrFacultyDropdown = new JComboBox<>(majors);
        majorOrFacultyDropdown.setFont(labelFont2);
        panel.add(createFormRow(dynamicLabel, majorOrFacultyDropdown));
        panel.add(Box.createVerticalStrut(20));

        //return panel with all components attached
        return panel;
    }

    /*
    This method takes the input from all the text fields and checks
    them using the ValidationUtil (established rules and restrictions
    for the information). If a restriction is breached the user is 
    alerted, otherwise the new user is saved to their respective
    table in the database.
     */
    @Override
    protected void handleSave() {
        //randomly generate an id for the new user using user specific method
        int id = ((NewUserInterface) currentObject).generateNewUserId();

        //get the rest of the information from the text fields
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dob = dobField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        //input validation checks
        if (!ValidationUtil.checkIntegerRange(firstName.length(), 1, 50)) {
            PopUpUtil.displayError("First name must be 1 to 50 characters,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkIntegerRange(lastName.length(), 1, 50)) {
            PopUpUtil.displayError("Last name must be 1 to 50 characters,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkDateOfBirth(dob)) {
            return;
        }

        if (!ValidationUtil.checkIntegerRange(password.length(), 8, 30)) {
            PopUpUtil.displayError("Passwords must be 8 to 30 characters,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkEmail(email)) {
            PopUpUtil.displayError("Invalid email address,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkPhoneNumber(phone)) {
            PopUpUtil.displayError("Invalid phone number,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkAddress(address)) {
            PopUpUtil.displayError("Invalid address format,\nplease try again.");
            return;
        }

        //retrieve gender from selected radio buttons, if nothing is selected ==> alert user
        Character gender = null;
        if (maleRadio.isSelected()) {
            gender = 'M';
        } else if (femaleRadio.isSelected()) {
            gender = 'F';
        } else {
            PopUpUtil.displayError("Please select a gender.");
            return;
        }

        //var for setting student major or faculty for lecturer from the dropdown box
        String majorOrFaculty = (String) majorOrFacultyDropdown.getSelectedItem();

        //automatically create uni email in format firstname.lastname@aut.ac.nz after checks have passed
        String uniEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@aut.ac.nz";

        //depending on type of user admin is wishing to create,
        //update currentObject in memory + in the relevant table within the database
        if (currentObject instanceof Student) {

            currentObject = new Student( //instansiate new Student in memory
                    id, password, firstName, lastName, dob, email, uniEmail,
                    ValidationUtil.formatPhoneNumber(phone), gender, address, majorOrFaculty
            );
        } else if (currentObject instanceof Lecturer) {

            currentObject = new Lecturer( //instansiate new Lecturer in memory
                    id, password, firstName, lastName, dob, email, uniEmail,
                    ValidationUtil.formatPhoneNumber(phone), gender, address, majorOrFaculty
            );
        }
        
        ((NewUserInterface) currentObject).addNewUserToDatabase();

        //go back to the main dashboard for the currently logged in admin
        UserController.getCurrentUser().userMainDashboard();
    }

    /*
    Helper method for handling going back to the main dashboard
    of the currently logged in admin.
     */
    @Override
    protected void handleBack() {
        int confirmation = PopUpUtil.displayConfirmInfo(
                "Are you sure you want to go back?\nYour changes won't be saved.");

        if (confirmation == JOptionPane.YES_OPTION) {
            UserController.getCurrentUser().userMainDashboard();
        }
    }
}
