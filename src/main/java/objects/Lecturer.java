/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import objects.objects_interfaces.NewUserInterface;
import abstract_classes.User;
import controller.UserController;
import dao.LecturerDAO;
import java.util.HashMap;
import java.util.Random;
import utility_classes.NavigationUtil;
import view.lecturer_view.LecturerDashboardView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The Lecturer class extends User and provides basic lecturer attributes and
 * methods to retrieve them. It also implements the NewUserInterface methods
 * for adding this Student to the Student table and generating a unique ID
 * for them.
 *
 */
public class Lecturer extends User implements NewUserInterface {

    protected String faculty;
    protected HashMap<String, Course> coursesTaught;

    /*
    Default constructor to create a blank user;
    used when admin creates a new lecturer user.
     */
    public Lecturer() {}

    public Lecturer(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address, String faculty) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
        this.faculty = faculty;
        this.coursesTaught = new HashMap<String, Course>();
    }

    public String getFaculty() {
        return this.faculty;
    }

    public HashMap<String, Course> getCoursesTaught() {
        return this.coursesTaught;
    }

    @Override
    public void saveCurrrentUser() {
        LecturerDAO lecturerDAO = new LecturerDAO();
        lecturerDAO.update(this);
        UserController.setCurrentUsers(lecturerDAO.getAllUsers());
    }

    @Override
    public void removeCurrentUser() {
        new LecturerDAO().removeUser(this);
    }

    @Override
    public void userMainDashboard() {
        NavigationUtil.newFrame(new LecturerDashboardView(this));
    }

    @Override
    public void addNewUserToDatabase() {
        //add this lecturer to the Lecturer table
        new LecturerDAO().createNewUser(this);
    }

    @Override
    public int generateNewUserId() {
        Random rand = new Random();
        
        //Lecturer ID range BETWEEN 14000000 AND 16000000 as specified in the Lecturer table
        int randomId = 14000000 + rand.nextInt(2000000);

        LecturerDAO lecturerDAO = new LecturerDAO();

        //while the id is taken, keep regenerating until a valid one is found
        while (lecturerDAO.getById(randomId) != null) {
            randomId = 14000000 + rand.nextInt(2000000);
        }
        return randomId; //return when valid id is found
    }
}
