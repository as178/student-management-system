/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import abstract_classes.UserModifyDetails;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class extends the abstract UserModifyDetails class for editing personal
 * details, which is used by every user. In this context, the currentStudent is
 * passed into the class and their get & set methods are used for information
 * access.
 *
 */
public class StudentModifyDetails extends UserModifyDetails {
    /*
    As all the logic and methods are defined within the UserModifyDetails class,
    we only need to have a constructor in this class which will pass in the
    currently logged in student (calling the super() constructor).    
     */
    public StudentModifyDetails(Student currentStudent) {
        super(currentStudent);
    }
}
