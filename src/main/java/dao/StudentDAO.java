/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import abstract_classes.User;
import dao.dao_interfaces.UserCreationDAOInterface;
import utility_classes.PopUpUtil;
import objects.Student;
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
 * This Data Access Object is responsible for any Student related queries. It is
 * derived from out original FilesManager class.
 *
 */
public class StudentDAO implements UserDAOInterface<Student>, UserCreationDAOInterface<Student> {

    private Connection currentConnection = DatabaseManager.getCurrentConnection();

    /*
    Method to get student by input of ID.
     */
    @Override
    public Student getById(int id) {
        String sqlStatement = "SELECT * FROM Student WHERE id = ?";
        Student student = null;

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                student = new Student(
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
                        rs.getString("major")
                );
            }
        } catch (SQLException e) {
            PopUpUtil.displayError("Failed to retrieve Student by ID.");
        }

        return student;
    }

    /*
    This method updates the Student passed into it into the
    Student table.
     */
    @Override
    public void update(Student student) {

        String sqlStatement = "UPDATE Student SET password = ?, first_name = ?, last_name = ?, date_of_birth = ?, "
                + "personal_email = ?, university_email = ?, phone_number = ?, gender = ?, address = ?, major = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, student.getPassword());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setDate(4, Date.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(5, student.getPersonalEmail());
            preparedStatement.setString(6, student.getUniEmail());
            preparedStatement.setString(7, student.getPhoneNumber());
            preparedStatement.setString(8, String.valueOf(student.getGender()));
            preparedStatement.setString(9, student.getAddress());
            preparedStatement.setString(10, student.getMajor());
            preparedStatement.setInt(11, student.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("An error occurred while updating Student.");
            return;
        }

        PopUpUtil.displayInfo("Details updated successfully!");
    }

    /*
    Returns a HashMap of String keys and User values, representing all
    IDs and Students in the Student table respectively.
     */
    @Override
    public HashMap<String, User> getAllUsers() {
        HashMap<String, User> allStudents = new HashMap<String, User>();
        String sqlStatement = "SELECT * FROM Student";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Student newStudent = new Student(
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
                        rs.getString("major")
                );

                allStudents.put(newStudent.getId() + "", newStudent);
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading all students from the Student table.");
        }

        return allStudents;
    }

    /*
    This method takes a Student user and inserts
    them into the Student table.
     */
    @Override
    public void createNewUser(Student newStudent) {

        String sqlStatement = "INSERT INTO Student (id, password, first_name, last_name, date_of_birth, personal_email, university_email, "
                + "phone_number, gender, address, major) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, newStudent.getId());
            preparedStatement.setString(2, newStudent.getPassword());
            preparedStatement.setString(3, newStudent.getFirstName());
            preparedStatement.setString(4, newStudent.getLastName());
            preparedStatement.setDate(5, Date.valueOf(newStudent.getDateOfBirth()));
            preparedStatement.setString(6, newStudent.getPersonalEmail());
            preparedStatement.setString(7, newStudent.getUniEmail());
            preparedStatement.setString(8, newStudent.getPhoneNumber());
            preparedStatement.setString(9, String.valueOf(newStudent.getGender()));
            preparedStatement.setString(10, newStudent.getAddress());
            preparedStatement.setString(11, newStudent.getMajor());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to register Student.");
            return;
        }

        PopUpUtil.displayInfo("The following Student was registered successfully!\n"
                + newStudent.getId() + ", " + newStudent.getFirstName() + " " + newStudent.getLastName());
    }

    /*
    This method takes a Student user and removes them from the database.
    It checks whether the student has any enrolled and previous courses,
    and if so, deletes those entries first before removing the student
    from the Student table.
     */
    @Override
    public void removeUser(Student student) {
        String sqlStatement1 = "DELETE FROM EnrolledCourse WHERE student_id = ?";
        String sqlStatement2 = "DELETE FROM PreviousCourse WHERE student_id = ?";
        String sqlStatement3 = "DELETE FROM Student WHERE id = ?";

        try {

            //setting autocommit to false in case rollback is needed
            currentConnection.setAutoCommit(false);

            //firstly we have to remove the student from their enrolled courses (EnrolledCourse table)
            PreparedStatement preparedStatement1 = currentConnection.prepareStatement(sqlStatement1);
            preparedStatement1.setInt(1, student.getId());
            preparedStatement1.executeUpdate();

            //then we have to remove their previously taken courses (PreviousCourse table)
            PreparedStatement preparedStatement2 = currentConnection.prepareStatement(sqlStatement2);
            preparedStatement2.setInt(1, student.getId());
            preparedStatement2.executeUpdate();

            //and finally we can remove the student from the database (Student table)
            PreparedStatement preparedStatement3 = currentConnection.prepareStatement(sqlStatement3);
            preparedStatement3.setInt(1, student.getId());

            //if no rows are affected after statement is executed
            if (preparedStatement3.executeUpdate() == 0) {
                currentConnection.rollback(); //rollback if no student was deleted
                PopUpUtil.displayError("No student found under following ID: " + student.getId());
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

            PopUpUtil.displayError("Failed to remove student under following ID: " + student.getId());

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
