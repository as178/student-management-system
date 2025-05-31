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
import dao.LecturerDAO;
import dao.StudentDAO;
import java.awt.Font;
import java.util.Random;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import objects.Admin;
import objects.Lecturer;
import objects.Student;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 */
public class AdminCreateUserView extends UserViewAndModifyDetailsView {

    private Admin currentAdmin;
    private User newUser;

    public AdminCreateUserView(User newUser, Admin currentAdmin) {
        super(newUser);
        this.newUser = newUser;
        this.currentAdmin = currentAdmin;
        this.setTitle("Student Management System: Create New User");
    }

    private JTextField firstNameField, lastNameField, dobField;

    //radio buttons for choosing gender
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;

    //choosing major / department 
    private JComboBox<String> majorOrDepartmentDropdown;
    private JLabel dynamicLabel;

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

        panel.add(Box.createVerticalStrut(10));
        panel.add(createLabel("Last Name:", labelFont));
        lastNameField = new JTextField();
        lastNameField.setFont(labelFont2);
        panel.add(lastNameField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Date of Birth (YYYY-MM-DD):", labelFont));
        dobField = new JTextField();
        dobField.setFont(labelFont2);
        panel.add(dobField);

        //Gender Stuff
        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Gender:", labelFont));
        //radio buttons
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        maleRadio.setFont(labelFont2);
        femaleRadio.setFont(labelFont2);
        //group the radio buttons
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        //set Jpanet 
        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new BoxLayout(genderPanel, BoxLayout.X_AXIS));
        genderPanel.add(maleRadio);
        genderPanel.add(Box.createHorizontalStrut(15));
        genderPanel.add(femaleRadio);

        //choosing major / department
        panel.add(Box.createVerticalStrut(20));
        dynamicLabel = createLabel("", labelFont);
        if (newUser instanceof Student) {
            dynamicLabel.setText("Major:");
        } else if (newUser instanceof Lecturer) {
            dynamicLabel.setText("Department:");
        }
        panel.add(dynamicLabel);

        String[] options = {
            "Computer Science",
            "Data Science",
            "Networks & Cybersecurity",
            "Digital Services",
            "Software Development"
        };
        majorOrDepartmentDropdown = new JComboBox<>(options);
        majorOrDepartmentDropdown.setFont(labelFont2);
        panel.add(majorOrDepartmentDropdown);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Password:", labelFont));
        passwordField = new JPasswordField();
        passwordField.setFont(labelFont2);
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Email:", labelFont));
        panel.add(createHintLabel("e.g. user@example.com"));
        emailField = new JTextField();
        emailField.setFont(labelFont2);
        panel.add(emailField);

        //add gender panel with radio buttons 
        panel.add(genderPanel);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Phone Number:", labelFont));
        panel.add(createHintLabel("e.g. xxx xxx xxxx"));
        phoneField = new JTextField();
        phoneField.setFont(labelFont2);
        panel.add(phoneField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(createLabel("Address:", labelFont));
        panel.add(createHintLabel("e.g. 123 Main Street"));
        addressField = new JTextField();
        addressField.setFont(labelFont2);
        panel.add(addressField);

        return panel;
    }

    @Override
    protected void handleSave() {
        int id = generateNewUserID(currentUser);
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dob = dobField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        //var for setting student major or depearment for lecturer
        String majorOrDept = (String) majorOrDepartmentDropdown.getSelectedItem();

        //set gender from radio buttons 
        Character gender = null;
        if (maleRadio.isSelected()) {
            gender = 'M';
        } else if (femaleRadio.isSelected()) {
            gender = 'F';
        } else {
            PopUpUtil.displayError("Please select gender,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkPassword(password)) {
            PopUpUtil.displayError("Passwords must be 8 to 30 characters,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkEmail(email)) {
            PopUpUtil.displayError("Invalid email address,\nplease try again.");
            return;
        }

        if (gender == null) {
            PopUpUtil.displayError("Please select a gender.");
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

        //word limit 1-50 for first and last name in validation util 
        if (!ValidationUtil.checkNameLength(firstName)) {
            PopUpUtil.displayError("First name must be from 1 - 50 characters,\nplease try again.");
            return;
        }

        if (!ValidationUtil.checkNameLength(lastName)) {
            PopUpUtil.displayError("Last name be from 1 - 50 characters,\nplease try again.");
            return;
        }

        //Create email from firstname.lastname.aut.ac.nz .lower()
        String uniEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@aut.ac.nz";

        /*
        if (newUser instanceof Student) {
            Student student = new Student(
                    id, password, firstName, lastName, dob, email, uniEmail,
                    ValidationUtil.formatPhoneNumber(phone), gender, address, majorOrDept
            );

            StudentDAO studentDAO = new StudentDAO();
            studentDAO.create(student);
            UserController.setCurrentUsers(studentDAO.getAllUsers());
            PopUpUtil.displayInfo("New Student created successfully!");
            NavigationUtil.newFrame(new AdminDashboardView(currentAdmin));
        } else if (newUser instanceof Lecturer) {
            Lecturer lecturer = new Lecturer(
                    id, password, firstName, lastName, dob, email, uniEmail,
                    ValidationUtil.formatPhoneNumber(phone), gender, address, majorOrDept
            );

            LecturerDAO lecturerDAO = new LecturerDAO();
            lecturerDAO.create(lecturer);
            UserController.setCurrentUsers(lecturerDAO.getAllUsers());
            PopUpUtil.displayInfo("Lecturer created successfully!");
            NavigationUtil.newFrame(new AdminDashboardView(currentAdmin));
        }
         */
    }

    //null or user obj
    private int generateNewUserID(User currentUser) {
        Random rand = new Random();
        if (currentUser instanceof Student) {
            //student id range id BETWEEN 20000000 AND 29999999
            int randomId = 20000000 + rand.nextInt(10000000);

            StudentDAO studentDAO = new StudentDAO();

            while (studentDAO.getById(randomId) != null) {
                //regenerate randomId
                randomId = 20000000 + rand.nextInt(10000000);
            }

            return randomId;

        } else if (currentUser instanceof Lecturer) {
            //lecturer id range id BETWEEN 14000000 AND 16000000
            int randomId = 14000000 + rand.nextInt(2000000);

            LecturerDAO lecturerDAO = new LecturerDAO();

            while (lecturerDAO.getById(randomId) != null) {
                //regenerate randomId
                randomId = 20000000 + rand.nextInt(10000000);
            }

            return randomId;
        }
        return 0;
    }

    @Override
    protected void handleBack() {
        int confirmation = PopUpUtil.displayConfirmInfo(
                "Are you sure you want to go back?\nYour changes won't be saved.");

        if (confirmation == JOptionPane.YES_OPTION) {
            NavigationUtil.newFrame(new AdminDashboardView(currentAdmin));
        }
    }
}
