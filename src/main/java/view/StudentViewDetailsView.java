/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import abstract_classes.UserViewAndModifyDetailsView;
import concrete_classes.student.Student;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when a Student wishes to view and/or modify their
 * personal information. Extends an abstract class which holds all the logic.
 */
public class StudentViewDetailsView extends UserViewAndModifyDetailsView {
    
    public StudentViewDetailsView(Student currentStudent) {
        super(currentStudent);
    }
}
