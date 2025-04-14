/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.students;

import concrete_classes.other.HeadersUtil;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;

/**
 *
 * @author Angela Saric (24237573)
 */
public class StudentModifyMajor implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Student currentStudent;

    public StudentModifyMajor(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    // - list the available majors, excluding the one student is already taking
    // - do some sort index selection, so that it does not matter how many
    //majors we add or remove, its dynamic
    // - prior to user choosing a new major, give alert that says all their
    //current courses will be wiped, moved to previous courses and assigned the
    //-1 "withdrawn" grade
    // - if they're ok with it, proceed and save their choice
    // - show message saying "to add your courses go back > select change courses
    
    @Override
    public void showMenu() {
        
    }

    @Override
    public void showHeader() {
        //HeadersUtil
    }

    @Override
    public String validateUserInput() {
        return "";
    }

}
