/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objects.objects_interfaces;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Interface implemented by users which enable creation of new such users within
 * the program. This interface correlates with the UserCreationDAOInterface.
 *
 */
public interface NewUserInterface {

    //add user to their respective database
    public boolean addNewUserToDatabase();

    //generate a unique id for that user within their given range
    public int generateNewUserId();

    //generate a unique uni email for that user 
    public String generateNewUniEmail(String firstName, String lastName, int id);
}
