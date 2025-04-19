/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import abstract_classes.UserModifyDetails;
import abstract_classes.UserViewDetails;

/**
 *
 * @author williamniven
 */
public class LecturerViewDetails extends UserViewDetails {

    public LecturerViewDetails(Lecturer currentLecturer) {
        super(currentLecturer);
    }

    @Override
    public void showExtraDetails() {
        System.out.println("Faculty: " + ((Lecturer) currentUser).getFaculty());
    }

    @Override
    protected UserModifyDetails modifyDetails() {
        return new LecturerModifyDetails((Lecturer) currentUser);
    }
}
