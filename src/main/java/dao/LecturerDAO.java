/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import abstract_classes.User;
import dao.dao_interfaces.UserCreationDAOInterface;
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
public class LecturerDAO implements UserDAOInterface<Lecturer>, UserCreationDAOInterface<Lecturer> {

    private Connection currentConnection = DatabaseManager.getCurrentConnection();

    /*
    Method to get student by input of ID.
     */
    @Override
    public Lecturer getById(int id) {
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
    public void update(Lecturer lecturer) {

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
            return;
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

    /*
    This method takes a Lecturer user and inserts
    them into the Lecturer table.
     */
    @Override
    public void createNewUser(Lecturer newLecturer) {

        String sqlStatement = "INSERT INTO Lecturer (id, password, first_name, last_name, date_of_birth, personal_email, university_email, "
                + "phone_number, gender, address, faculty) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, newLecturer.getId());
            preparedStatement.setString(2, newLecturer.getPassword());
            preparedStatement.setString(3, newLecturer.getFirstName());
            preparedStatement.setString(4, newLecturer.getLastName());
            preparedStatement.setDate(5, Date.valueOf(newLecturer.getDateOfBirth()));
            preparedStatement.setString(6, newLecturer.getPersonalEmail());
            preparedStatement.setString(7, newLecturer.getUniEmail());
            preparedStatement.setString(8, newLecturer.getPhoneNumber());
            preparedStatement.setString(9, String.valueOf(newLecturer.getGender()));
            preparedStatement.setString(10, newLecturer.getAddress());
            preparedStatement.setString(11, newLecturer.getFaculty());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to register Lecturer.");
            return;
        }

        PopUpUtil.displayInfo("The following Lecturer was registered successfully!\n"
                + newLecturer.getId() + ", " + newLecturer.getFirstName() + " " + newLecturer.getLastName());
    }

    /*
    This method takes a Lecturer user and removes them from the database.
    It checks whether the lecturer teaches any courses, and if so, nullifies
    the courses' lecturer_id fields prior to removing the lecturer from the Lecturer table.
     */
    @Override
    public void removeUser(Lecturer lecturer) {
        String sqlStatement1 = "UPDATE Course SET lecturer_id = NULL WHERE lecturer_id = ?";
        String sqlStatement2 = "DELETE FROM Lecturer WHERE id = ?";

        try {
            //setting autocommit to false in case rollback is needed
            currentConnection.setAutoCommit(false);

            //firstly, iterate through the Course table and see whether
            //the lecturer is teaching any courses, and if so, replace their ID will NULL
            PreparedStatement preparedStatement1 = currentConnection.prepareStatement(sqlStatement1);
            preparedStatement1.setInt(1, lecturer.getId());
            preparedStatement1.executeUpdate();

            //once the lecturer is no longer teaching any courses
            //we can safely delete them from the Lecturer table
            PreparedStatement preparedStatement2 = currentConnection.prepareStatement(sqlStatement2);
            preparedStatement2.setInt(1, lecturer.getId());

            //if no rows are affected after statement is executed
            if (preparedStatement2.executeUpdate() == 0) {
                currentConnection.rollback(); //rollback if no lecturer was deleted
                PopUpUtil.displayError("No lecturer found under following ID: " + lecturer.getId());
                return;
            }

            //if everything was done successfully, commit
            currentConnection.commit();

        //if any errors occured throughout the process
        } catch (SQLException e) {
            try {
                currentConnection.rollback(); //undo potentially partial modifications
            
            } catch (SQLException ex) {
                PopUpUtil.displayError("Rollback failure.");
            }
           
            PopUpUtil.displayError("Failed to remove lecturer under following ID: " + lecturer.getId());

        //reset auto commit feature back regardless of outcome
        } finally {
            try {
                currentConnection.setAutoCommit(true);
            } catch (SQLException ex) {
                PopUpUtil.displayError("AutoCommit failure.");
            }
        }
    }
}
