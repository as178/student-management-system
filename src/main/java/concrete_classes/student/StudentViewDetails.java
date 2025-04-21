/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import abstract_classes.UserModifyDetails;
import abstract_classes.UserViewDetails;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class extends the abstract UserViewDetails class for viewing personal
 * details, which is used by every user. In this context, the currentStudent is
 * passed into the class and their get & set methods are used for information
 * access.
 *
 */
public class StudentViewDetails extends UserViewDetails {

    /*
    As all the logic and methods are defined within the UserViewDetails class,
    we only need to have a constructor which will pass in the
    currently logged in student (calling the super() constructor).
    
    We also need to override the abstract modifyDetails method within this
    viewing class, in order for the method to return the appropriate modify class
    (in this case it returns an StudentModifyDetails class, with the currently logged
    in student being passed into it; the FilesManager currentUser, casted to Student).
     */
    
    public StudentViewDetails(Student currentStudent) {
        super(currentStudent);
    }

    @Override
    protected UserModifyDetails modifyDetails() {
        return new StudentModifyDetails((Student) currentUser);
    }
}
