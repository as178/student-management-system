/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.dao_interfaces;

import abstract_classes.User;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Interface used by each User DAO for their respective User related methods.
 *
 */
public interface UserDAOInterface<T extends User> {

    public void update(T user);

    public T getById(int id);

    public HashMap<String, User> getAllUsers();

    public void removeUser(T user);
}
