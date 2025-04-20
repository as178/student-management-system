/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.admin;

import abstract_classes.UserModifyDetails;

/**
 *
 * @author williamniven
 */
public class AdminModifyDetails extends UserModifyDetails {
    //allows user to modify details 
    public AdminModifyDetails(Admin currentAdmin) {
        super(currentAdmin);
    }
}
