/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import abstract_classes.User;
import controller.UserController;
import dao.AdminDAO;
import utility_classes.NavigationUtil;
import view.admin_view.AdminDashboardView;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The Admin class extends User and possesses all its generic attributes and
 * methods.
 *
 */
public class Admin extends User {

    public Admin(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
    }

    @Override
    public void saveCurrrentUser() {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.update(this);
        UserController.setCurrentUsers(adminDAO.getAllUsers());
    }

    @Override
    public void removeCurrentUser() {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.removeUser(this);
    }

    @Override
    public void userMainDashboard() {
        NavigationUtil.newFrame(new AdminDashboardView(this));
    }
}
