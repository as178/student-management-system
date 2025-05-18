/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import concrete_classes.other.ProgramShutdownUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Database manager class which initializes the database and makes the tables,
 * as well as making sure that none of the tables already exist.
 *
 */
public final class DatabaseManager {

    //Statement to create the database + connection 
    private static final String url = "jdbc:derby:StudentManagementSystemDB; create=true";
    private static Connection connection;

    /*
    Constructor to initalise the database and its tables
    when called. Depending on the initialisation status of the
    database, the program will either proceed to make tables
    or stop in order to prevent further errors from occuring.
     */
    public DatabaseManager() {

        if (initialiseConnection()) {
            //proceed with creating the tables
            //createTables();
        } else {
            //shut down program
            ProgramShutdownUtil.shutdown();
        }
    }

    /*
    Initialises the connection to the databases, returning
    a boolean to confirm state of the initilisation.
     */
    public static boolean initialiseConnection() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Initial connection to database has successfully established.");
            return true;
        } catch (SQLException ex) {
            System.out.println("Initial connection to database has failed to establish.");
            return false;
        }
    }

    /*
    Closes the connection to the database, or otherwise lets
    the user know the connection failed to close.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("The connection to the database has closed successfully.");
            } catch (SQLException ex) {
                System.out.println("The connection to the database has failed to close.");
            }
        }
    }
}
