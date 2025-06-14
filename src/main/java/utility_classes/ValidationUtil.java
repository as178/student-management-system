/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility_classes;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class has validation methods used to check and validate user input; it
 * enforces restrictions required for certain fields within the program.
 *
 */
public final class ValidationUtil {

    private ValidationUtil() {}

    //users within the program must be at least 16 years old
    public static boolean checkDateOfBirth(String userInputDOB) {

        //set date format same as is in the database
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate dateOfBirth = LocalDate.parse(userInputDOB, format);

            //make sure the user is at least 16 years old
            //by checking the length of time between now
            //and when the user was born
            LocalDate today = LocalDate.now();
            int years = Period.between(dateOfBirth, today).getYears();

            if (years < 16) {
                PopUpUtil.displayError("The user must be at least 16 years old.");
                return false;
            }

        } catch (DateTimeParseException e) {
            PopUpUtil.displayError("Invalid date and/or format, please\nensure a YYYY-MM-DD format is being used.");
            return false;
        }

        //else if the date of birth entered is correctly formatted + appropriate ==> return true
        return true;
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
        //removes whitespaces from the phone number, replacing them with an empty string
        return phoneNumber.replaceAll("\\s+", "");
    }

    public static boolean checkAddress(String address) {
        //check format of address is "number, 4–6 words, then 4 digit postcode"
        //e.g. 123 Street Name Suburb City 1234
        //ensures no commas are included (aesthetic choice in this project)
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

    //version of above to be called when dealing with float values
    public static boolean checkFloatRange(String inputFloat, float lowestFloat, float highestFloat) {
        //if the inputFloat String is empty or null
        if (inputFloat == null || inputFloat.trim().isBlank()) {
            return false;
        }
        try {
            //otherwise parse into a valid float 
            float validFloat = Float.parseFloat(inputFloat.trim());
            //check if the float is within the given range
            return validFloat >= lowestFloat && validFloat <= highestFloat;
        } catch (NumberFormatException e) { //if it's not a valid float, return false
            return false;
        }
    }
}
