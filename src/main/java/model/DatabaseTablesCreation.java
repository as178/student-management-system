/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import concrete_classes.other.PopUpUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class provides the methods to create all the necessary tables for our
 * program as well as the insertion of initial data that comes with the program.
 * If any tables already exist, there is a method in place to ensure they are
 * dropped before being created.
 *
 */
public class DatabaseTablesCreation {

    private Connection connection;
    private Statement statement;

    //Constructor to pass in the connection from the DatabaseManager
    public DatabaseTablesCreation(Connection connection) {
        this.connection = connection;
    }

    /*
    This method will create our tables, calling each create method in
    appropriate order, such that no foreign key mappings cause errors
    due to tables not existing.
     */
    public void createTables() {
        try {
            this.statement = this.connection.createStatement();

            if (!isExistingTable("Major")) {
                createMajorTable();
            }
            if (!isExistingTable("Lecturer")) {
                createLecturerTable();
            }
            if (!isExistingTable("Course")) {
                createCourseTable();
            }
            if (!isExistingTable("Student")) {
                createStudentTable();
            }
            if (!isExistingTable("Admin")) {
                createAdminTable();
            }
            if (!isExistingTable("EnrolledCourse")) {
                createEnrolledCourseTable();
            }
            if (!isExistingTable("PreviousCourse")) {
                createPreviousCourseTable();
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create statement.");
        }
    }

    /*
    Below are methods for creating individual tables.
     */
    private void createMajorTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Major (\n"
                    + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
                    + "name VARCHAR(100) UNIQUE NOT NULL)");
        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create Major table.");
        }
    }

    private void createAdminTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Admin (\n"
                    + "id INT PRIMARY KEY,\n"
                    + "password VARCHAR(100),\n"
                    + "first_name VARCHAR(50),\n"
                    + "last_name VARCHAR(50),\n"
                    + "date_of_birth DATE,\n"
                    + "personal_email VARCHAR(100),\n"
                    + "university_email VARCHAR(100),\n"
                    + "phone_number VARCHAR(20),\n"
                    + "gender CHAR(1),\n"
                    + "address VARCHAR(255)\n)");
        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create Admin table.");
        }
    }

    private void createLecturerTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Lecturer (\n"
                    + "id INT PRIMARY KEY,\n"
                    + "password VARCHAR(100),\n"
                    + "first_name VARCHAR(50),\n"
                    + "last_name VARCHAR(50),\n"
                    + "date_of_birth DATE,\n"
                    + "personal_email VARCHAR(100),\n"
                    + "university_email VARCHAR(100),\n"
                    + "phone_number VARCHAR(20),\n"
                    + "gender CHAR(1),\n"
                    + "address VARCHAR(255),\n"
                    + "faculty VARCHAR(100)\n)");
        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create Lecturer table.");
        }
    }

    private void createCourseTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Course (\n"
                    + "code VARCHAR(10) PRIMARY KEY,\n"
                    + "name VARCHAR(100),\n"
                    + "major VARCHAR(100),\n"
                    + "prerequisite_code VARCHAR(10),\n"
                    + "credits INT,\n"
                    + "lecturer_id INT,\n"
                    + "description VARCHAR(500),\n"
                    + "FOREIGN KEY (lecturer_id) REFERENCES Lecturer(id),\n"
                    + "FOREIGN KEY (prerequisite_code) REFERENCES Course(code)\n)");
        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create Course table.");
        }
    }

    private void createStudentTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Student (\n"
                    + "id INT PRIMARY KEY,\n"
                    + "password VARCHAR(100),\n"
                    + "first_name VARCHAR(50),\n"
                    + "last_name VARCHAR(50),\n"
                    + "date_of_birth DATE,\n"
                    + "personal_email VARCHAR(100),\n"
                    + "university_email VARCHAR(100),\n"
                    + "phone_number VARCHAR(20),\n"
                    + "gender CHAR(1),\n"
                    + "address VARCHAR(255),\n"
                    + "major_id INT,\n"
                    + "FOREIGN KEY (major_id) REFERENCES Major(id)\n)");
        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create Student table.");
        }
    }

    private void createEnrolledCourseTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE EnrolledCourse (\n"
                    + "student_id INT,\n"
                    + "course_code VARCHAR(10),\n"
                    + "grade FLOAT,\n"
                    + "PRIMARY KEY (student_id, course_code),\n"
                    + "FOREIGN KEY (student_id) REFERENCES Student(id),\n"
                    + "FOREIGN KEY (course_code) REFERENCES Course(code)\n)");
        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create EnrolledCourse table.");
        }
    }

    private void createPreviousCourseTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE PreviousCourse (\n"
                    + "student_id INT,\n"
                    + "course_code VARCHAR(10),\n"
                    + "grade FLOAT,\n"
                    + "PRIMARY KEY (student_id, course_code),\n"
                    + "FOREIGN KEY (student_id) REFERENCES Student(id),\n"
                    + "FOREIGN KEY (course_code) REFERENCES Course(code)\n)");
        } catch (SQLException ex) {
            PopUpUtil.displayError("Failed to create PreviousCourse table.");
        }
    }

    /*
    A method to check whether a table exists in the program database already,
    appropriate boolean value is returned.
     */
    private boolean isExistingTable(String givenTableName) {
        try {
            DatabaseMetaData metaData = this.connection.getMetaData();
            String[] stringArray = {"TABLE"};
            ResultSet resultSet = metaData.getTables(null, null, null, stringArray);

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(givenTableName) == 0) {
                    return true;
                }
            }

            if (resultSet != null) {
                resultSet.close();
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in checking if following table exists: " + givenTableName);
        }

        return false;
    }

    /*
    Below are methods for data insertion.
     */

}
