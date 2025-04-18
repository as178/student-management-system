/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.admin;

import abstract_classes.UserModifyDetails;
import abstract_classes.UserViewDetails;

/**
 *
 * @author williamniven
 */
public class AdminViewDetails extends UserViewDetails {
    
    public AdminViewDetails(Admin currentAdmin){
        super(currentAdmin);
    }
    
    @Override
    protected UserModifyDetails modifyDetails() {
        return new AdminModifyDetails((Admin) currentUser);
    }
}
