/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

/**
 *
 * @author Angela Saric (24237573)
 * 
 */
public abstract class User {
    protected int id;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String dateOfBirth;
    protected String personalEmail;
    protected String uniEmail;
    protected String phoneNumber;
    protected Character gender;
    protected String address;

    public User(int id, String password, String firstName, String lastName, String dateOfBirth, String personalEmail,
            String uniEmail, String phoneNumber, Character gender, String address) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.personalEmail = personalEmail;
        this.uniEmail = uniEmail;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
    }
}
