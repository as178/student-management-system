/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.student;

import abstract_classes.UserModifyDetails;
import abstract_classes.UserViewDetails;

/**
 *
 * @author williamniven
 */
public class StudentViewDetails extends UserViewDetails {

    public StudentViewDetails(Student currentStudent) {
        super(currentStudent);
    }

    @Override
    protected UserModifyDetails modifyDetails() {
        return new StudentModifyDetails((Student) currentUser);
    }
}