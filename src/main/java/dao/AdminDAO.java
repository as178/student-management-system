/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import abstract_classes.User;
import objects.Admin;
import utility_classes.PopUpUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import model.DatabaseManager;
import dao.dao_interfaces.UserDAOInterface;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Data Access Object is responsible for any Admin related queries. It is
 * derived from out original FilesManager class.
 *
 */
public class AdminDAO implements UserDAOInterface<Admin> {

    private Connection currentConnection = DatabaseManager.getCurrentConnection();

    /*
    This method updates the Admin passed into it into the
    Admin table.
     */
    @Override
    public void update(Admin admin) {

        String sqlStatement = "UPDATE Admin SET password = ?, first_name = ?, last_name = ?, date_of_birth = ?, "
                + "personal_email = ?, university_email = ?, phone_number = ?, gender = ?, address = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, admin.getPassword());
            preparedStatement.setString(2, admin.getFirstName());
            preparedStatement.setString(3, admin.getLastName());
            preparedStatement.setDate(4, Date.valueOf(admin.getDateOfBirth()));
            preparedStatement.setString(5, admin.getPersonalEmail());
            preparedStatement.setString(6, admin.getUniEmail());
            preparedStatement.setString(7, admin.getPhoneNumber());
            preparedStatement.setString(8, String.valueOf(admin.getGender()));
            preparedStatement.setString(9, admin.getAddress());
            preparedStatement.setInt(10, admin.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }

        PopUpUtil.displayInfo("Details updated successfully!");
    }

    /*
    Returns a HashMap of String keys and User values, representing all
    IDs and Admins in the Admin table respectively.
     */
    @Override
    public HashMap<String, User> getAllUsers() {
        HashMap<String, User> allAdmins = new HashMap<String, User>();
        String sqlStatement = "SELECT * FROM Admin";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Admin newAdmin = new Admin(
                        rs.getInt("id"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("date_of_birth"),
                        rs.getString("personal_email"),
                        rs.getString("university_email"),
                        rs.getString("phone_number"),
                        rs.getString("gender").charAt(0),
                        rs.getString("address")
                );

                allAdmins.put(newAdmin.getId() + "", newAdmin);
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading all admins from the Admin table.");
        }

        return allAdmins;
    }

    /*
    Method to get Admin by input of ID.
     */
    @Override
    public Admin getById(int id) {
        String sqlStatement = "SELECT * FROM Admin WHERE id = ?";
        Admin admin = null;

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                admin = new Admin(
                        rs.getInt("id"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("date_of_birth"),
                        rs.getString("personal_email"),
                        rs.getString("university_email"),
                        rs.getString("phone_number"),
                        rs.getString("gender").charAt(0),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            PopUpUtil.displayError("Failed to retrieve Admin by ID.");
        }

        return admin;
    }

    /*
    Method to remove an Admin from Admin table.
     */
    @Override
    public void removeUser(Admin admin) {

        String sqlStatement = "DELETE FROM Admin WHERE id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, admin.getId());

            //if no rows are affected after statement is executed
            if (preparedStatement.executeUpdate() == 0) {
                PopUpUtil.displayError("No admin found under following ID: " + admin.getId());
                return;
            }
        } catch (SQLException e) {
            PopUpUtil.displayError("Failed to remove admin under following ID: " + admin.getId());
            return;
        }

        PopUpUtil.displayInfo("Successfully removed admin with following ID: " + admin.getId());
    }
}
