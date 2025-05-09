/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class has validation methods used when userInput is requested; it
 * enforces restrictions required for certain fields within the program.
 *
 */
public final class ValidationUtil {

    private ValidationUtil() {}

    /*
    User passwords must:
    - not be empty
    - be between 8 and 30 chars
    - not contain commas or periods
     */
    public static boolean checkPassword(String password) {
        return !password.isBlank()
                && password.length() >= 8
                && password.length() <= 30
                && !password.contains(",")
                && !password.contains(".");
    }

    public static boolean checkEmail(String email) {
        //the following regex enforces an email styled address and
        //ensures addresses are 50 chars or less in length
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
                && email.length() <= 50;
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        //calls the format method to remove whitespace first
        //then checks phone number has either:
        //exactly 10 digits (e.g. 0211234567)
        //begins with a "+" followed by 11 to 13 digits (e.g. +64211234567)
        return formatPhoneNumber(phoneNumber).matches("^\\d{10}$")
                || formatPhoneNumber(phoneNumber).matches("^\\+\\d{11,13}$");
    }

    public static String formatPhoneNumber(String phoneNumber) {
        //removes whitespaces from the phonenumber, replacing them with an empty string
        return phoneNumber.replaceAll("\\s+", "");
    }

    public static boolean checkAddress(String address) {
        //check format of address is "number, 4–6 words, then 4 digit postcode"
        //e.g. 123 Street Name Suburb City 1234
        //ensures no commas are included
        return address.matches("^\\d+\\s+(?:[A-Za-z-]+\\s+){3,5}[A-Za-z-]+\\s+\\d{4}$")
                && !address.contains(",");
    }

    public static boolean checkIntegerRange(String inputNumber, int lowestNumber, int highestNumber) {
        //if the inputNumber String is empty or null
        if (inputNumber == null || inputNumber.trim().isBlank()) {
            return false;
        }
        try {
            //otherwisse parse into a validNumber Integer
            int validNumber = Integer.parseInt(inputNumber.trim());
            //check if the integer is within the given range
            return checkIntegerRange(validNumber, lowestNumber, highestNumber);
        } catch (NumberFormatException e) { //if it's not a valid number, return false
            return false;
        }
    }

    //version of method to be called whenever the input is an integer
    public static boolean checkIntegerRange(int inputNumber, int lowestNumber, int highestNumber) {
        return inputNumber >= lowestNumber && inputNumber <= highestNumber; //returns whether the input number is within given range
    }
}
