/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.admin_view;

import abstract_classes.UserViewAndModifyDetailsView;
import objects.Admin;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * View which is shown when an Admin wishes to view and/or modify their
 * personal information. Extends the UserViewAndModifyDetailsView abstract class
 * which holds all the logic.
 *
 */
public class AdminViewDetailsView extends UserViewAndModifyDetailsView {

    public AdminViewDetailsView(Admin currentAdmin) {
        super(currentAdmin);
    }
}