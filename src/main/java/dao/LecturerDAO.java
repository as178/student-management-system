/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import abstract_classes.User;
import objects.Lecturer;
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
 * This Data Access Object is responsible for any Lecturer related queries. It
 * is derived from out original FilesManager class.
 *
 */
public class LecturerDAO implements UserDAOInterface<User> {

    private Connection currentConnection = DatabaseManager.getCurrentConnection();

    /*
    Method to get student by input of ID.
     */
    @Override
    public User getById(int id) {
        String sqlStatement = "SELECT * FROM Lecturer WHERE id = ?";
        Lecturer lecturer = null;

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lecturer = new Lecturer(
                        rs.getInt("id"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("date_of_birth"),
                        rs.getString("personal_email"),
                        rs.getString("university_email"),
                        rs.getString("phone_number"),
                        rs.getString("gender").charAt(0),
                        rs.getString("address"),
                        rs.getString("faculty")
                );
            }
        } catch (SQLException e) {
            PopUpUtil.displayError("Failed to retrieve Lecturer by ID.");
        }

        return lecturer;
    }

    /*
    This method updates the Lecturer passed into it into the
    Lecturer table.
     */
    @Override
    public void update(User user) {

        Lecturer lecturer = (Lecturer) user;

        String sqlStatement = "UPDATE Lecturer SET password = ?, first_name = ?, last_name = ?, date_of_birth = ?, "
                + "personal_email = ?, university_email = ?, phone_number = ?, gender = ?, address = ?, faculty = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, lecturer.getPassword());
            preparedStatement.setString(2, lecturer.getFirstName());
            preparedStatement.setString(3, lecturer.getLastName());
            preparedStatement.setDate(4, Date.valueOf(lecturer.getDateOfBirth()));
            preparedStatement.setString(5, lecturer.getPersonalEmail());
            preparedStatement.setString(6, lecturer.getUniEmail());
            preparedStatement.setString(7, lecturer.getPhoneNumber());
            preparedStatement.setString(8, String.valueOf(lecturer.getGender()));
            preparedStatement.setString(9, lecturer.getAddress());
            preparedStatement.setString(10, lecturer.getFaculty());
            preparedStatement.setInt(11, lecturer.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("An error occurred while updating Lecturer.");
        }

        PopUpUtil.displayInfo("Details updated successfully!");
    }

    /*
    Returns a HashMap of String keys and User values, representing all
    IDs and Lecturers in the Lecturer table respectively.
     */
    @Override
    public HashMap<String, User> getAllUsers() {
        HashMap<String, User> allLecturers = new HashMap<String, User>();
        String sqlStatement = "SELECT * FROM Lecturer";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Lecturer newLecturer = new Lecturer(
                        rs.getInt("id"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("date_of_birth"),
                        rs.getString("personal_email"),
                        rs.getString("university_email"),
                        rs.getString("phone_number"),
                        rs.getString("gender").charAt(0),
                        rs.getString("address"),
                        rs.getString("faculty")
                );

                allLecturers.put(newLecturer.getId() + "", newLecturer);
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading all lecturers from the Lecturer table.");
        }

        return allLecturers;
    }
}
