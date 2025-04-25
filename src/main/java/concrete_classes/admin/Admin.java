/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.admin;

import abstract_classes.User;
import concrete_classes.file_input_output.FilesManager;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The Admin class extends User and overrides the toString method
 * which is later on used within the FilesManager class. The getUsersPath
 * method is overriden to return the allAdmins.txt file.
 * 
 */
public class Admin extends User {

    public Admin(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
    }

    @Override
    public String toString() {
        return id + "," + password + "," + firstName + "," + lastName + ","
                + dateOfBirth + "," + personalEmail + "," + uniEmail + ","
                + phoneNumber + "," + gender + "," + address;
    }

    @Override
    public String getUsersPath() {
        return FilesManager.allAdminsFile;
    }
}