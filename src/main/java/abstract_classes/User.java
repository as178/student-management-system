/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * The abstract class that is extended by every user within
 * our program, as they all share similar attributes, and
 * methods required to retrieve/modify those attributes.
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
    
    public int getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getPersonalEmail() {
        return this.personalEmail;
    }

    public String getUniEmail() {
        return this.uniEmail;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Character getGender() {
        return this.gender;
    }

    public String getAddress() {
        return this.address;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setPersonalEmail(String newPersonalEmail) {
        this.personalEmail = newPersonalEmail;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void setAddress(String newAddress) {
        this.address = newAddress;
    }
}