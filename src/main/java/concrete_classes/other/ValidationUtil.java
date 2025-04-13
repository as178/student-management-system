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

    public static boolean checkPassword(String password) {
        return !password.isBlank() && password.length() >= 8;
    }

    public static boolean checkEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        return formatPhoneNumber(phoneNumber).matches("^\\d{10}$") ||
                formatPhoneNumber(phoneNumber).matches("^\\+\\d{11,13}$");
    }
    
    public static String formatPhoneNumber(String phoneNumber){
        return phoneNumber.replaceAll("\\s+", "");
    }

    public static boolean checkAddress(String address) {
        return address.matches("^\\d+\\s+[A-Za-z]+(?:\\s+[A-Za-z]+){2}\\s+\\d{4}$");
    }
}