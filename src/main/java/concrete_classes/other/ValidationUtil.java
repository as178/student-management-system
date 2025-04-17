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
        return !password.isBlank()
                && password.length() >= 8
                && password.length() <= 30
                && !password.contains(",")
                && !password.contains(".");
    }

    public static boolean checkEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
                && email.length() <= 50;
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        return formatPhoneNumber(phoneNumber).matches("^\\d{10}$")
                || formatPhoneNumber(phoneNumber).matches("^\\+\\d{11,13}$");
    }

    public static String formatPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("\\s+", "");
    }

    public static boolean checkAddress(String address) {
        return address.matches("^\\d+\\s+(?:[A-Za-z-]+\\s+){3,5}[A-Za-z-]+\\s+\\d{4}$")
                && !address.contains(",");
    }

    public static boolean checkIntegerRange(String inputNumber, int lowestNumber, int highestNumber) {
        if (inputNumber == null || inputNumber.trim().isBlank()) {
            return false;
        }
        try {
            int validNumber = Integer.parseInt(inputNumber.trim());
            return validNumber >= lowestNumber && validNumber <= highestNumber;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}