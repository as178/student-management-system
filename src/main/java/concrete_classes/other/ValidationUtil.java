/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573)
 */
public final class ValidationUtil {
    
    private ValidationUtil() {}
    
    public static boolean checkEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{3} \\d{3} \\d{4}$");
    }

    public static boolean checkAddress(String address) {
        return address.matches("^\\d+\\s+[^,]+,\\s*[^,]+,\\s*[^,]+,\\s*\\d{4}$");
    }    
    
}
