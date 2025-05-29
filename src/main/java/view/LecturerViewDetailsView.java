/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import abstract_classes.UserViewAndModifyDetailsView;
import concrete_classes.lecturer.Lecturer;

/**
 *
 * @author williamniven
 */
public class LecturerViewDetailsView extends UserViewAndModifyDetailsView {
    
    public LecturerViewDetailsView(Lecturer currentLecturer){
        super(currentLecturer);
    }
}
