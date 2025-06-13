/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utility_classes.PopUpUtil;
import dao.dao_interfaces.MajorDAOInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DatabaseManager;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Data Access Object is responsible for any Major related queries. It is
 * derived from out original FilesManager class.
 *
 */
public class MajorDAO implements MajorDAOInterface {

    private Connection currentConnection = DatabaseManager.getCurrentConnection();

    /*
    This method reads all majors from the Major table and
    stores them in a String arraylist.
     */
    public ArrayList<String> readAllMajors() {
        ArrayList<String> allMajors = new ArrayList<String>();
        String sqlStatement = "SELECT name FROM Major";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String major = rs.getString("name");
                allMajors.add(major);
            }
        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading majors from Major table.");
        }

        return allMajors;
    }
}
