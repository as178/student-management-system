/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.dao_interfaces;

import abstract_classes.User;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Interface used by User DAOs which enable creation of new
 * users within the database.
 *
 */
public interface UserCreationDAOInterface<T extends User> {

    public void createNewUser(T user);
}
