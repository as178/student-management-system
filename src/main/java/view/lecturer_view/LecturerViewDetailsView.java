/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.lecturer_view;

import abstract_classes.UserViewAndModifyDetailsView;
import objects.Lecturer;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a Lecturer wishes to view and/or modify their
 * personal information. Extends the UserViewAndModifyDetailsView abstract class
 * which holds all the logic.
 *
 */
public class LecturerViewDetailsView extends UserViewAndModifyDetailsView {
    
    public LecturerViewDetailsView(Lecturer currentLecturer){
        super(currentLecturer);
    }
}