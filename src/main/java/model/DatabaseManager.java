/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import utility_classes.PopUpUtil;
import utility_classes.ProgramShutdownUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Database manager class which initialises the database and controls the
 * connection status.
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
    and populate the tables or shutdown in order to prevent further
    errors from occuring.
     */
    public DatabaseManager() {

        if (this.initialiseConnection()) {
            //proceed with creating the tables
            DatabaseTablesCreation tablesCreation = new DatabaseTablesCreation(this.connection);
            tablesCreation.createTables();
        } else {
            //shut down program
            ProgramShutdownUtil.shutdown();
        }
    }

    /*
    Returns current connection for consistency and maintenance.
    */
    public static Connection getCurrentConnection(){
        return DatabaseManager.connection;
    }
    
    /*
    Initialises the connection to the databases, returning
    a boolean to confirm state of the initilisation.
     */
    public static boolean initialiseConnection() {
        try {
            connection = DriverManager.getConnection(url);
            return true;
        } catch (SQLException ex) {
            PopUpUtil.displayError("Initial connection to database has failed to establish.");
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
            } catch (SQLException ex) {
                PopUpUtil.displayError("The connection to the database has failed to close.");
            }
        }
    }
}
