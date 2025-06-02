/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility_classes;

import javax.swing.JOptionPane;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This utility class has methods widely used throughout the program to provide
 * small, general purpose pop-ups with custom messages depending on the context.
 *
 */
public final class PopUpUtil {

    //test mode flag to supress popups 
    public static boolean testMode = false;

    private PopUpUtil() {
    }

    /*
    Informative pop-ups, with small customisations for Information, Errors & Warnings.
    If testMode is true then error message printed to consolse for the test results window 
     */
    public static void displayInfo(String message) {
        if (!testMode) {
            JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("[INFO] " + message);
        }
    }

    public static void displayError(String message) {
        if (!testMode) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("[ERROR] " + message);
        }
    }

    public static void displayWarning(String message) {
        if (!testMode) {
            JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            System.out.println("[WARNING] " + message);
        }
    }

    /*
    Confirmation pop-ups for user actions (Information) and alerts (Warnings).
     */
    public static int displayConfirmWarning(String message) {
        if (!testMode) {
            return JOptionPane.showConfirmDialog(
                    null, message, "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            System.out.println("[CONFIRM WARNING] " + message);
            return JOptionPane.NO_OPTION; //simulate no option
        }
    }

    public static int displayConfirmInfo(String message) {
        if (!testMode) {
            return JOptionPane.showConfirmDialog(
                    null, message, "Information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("[CONFIRM INFO] " + message);
            return JOptionPane.NO_OPTION; //simulate no option
        }
    }

    /*
    Input pop-up for user input.
     */
    public static String displayInputInfo(String message) {
        if (!testMode) {
            return JOptionPane.showInputDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("[INPUT REQUEST] " + message);
            return ""; // simulate empty input or adjust as needed
        }
    }

    /*
    A specific method used by Admins when creating new users.
    Asks admin which user type to create, student / lecturer;
    2 buttons are displayed with each user type and a string
    is returned depending on selected type.
     */
    public static String displayUserTypeSelection() {
        if (!testMode) {
            String[] options = {"Student", "Lecturer"}; //two types

            //options pop-up to let the admin choose
            int selection = JOptionPane.showOptionDialog(null,
                    "Which type of user would you like to create?",
                    "Select User Type",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0] //default selection
            );

            //return selected option or null if cancelled 
            switch (selection) {
                case 0:
                    return "Student";
                case 1:
                    return "Lecturer";
                default:
                    return null;
            }
        }
        return "Student"; //Simulate default choice during testing
    }
}
