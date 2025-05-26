/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import abstract_classes.User;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The Admin class extends User and possesses all its generic
 * attributes.
 * 
 */
public class Admin extends User {

    public Admin(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address) {
        super(id, password, firstName, lastName, dateOfBirth, personalEmail, uniEmail, phoneNumber, gender, address);
    }
}