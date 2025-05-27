/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

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

    private PopUpUtil() {}

    /*
    Informative pop-ups, with small customisations for Information, Errors & Warnings.
     */
    public static void displayInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void displayError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void displayWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    /*
    Confirmation pop-ups for user actions (Information) and alerts (Warnings).
     */
    public static int displayConfirmWarning(String message) {
        return JOptionPane.showConfirmDialog(
                null, message, "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public static int displayConfirmInfo(String message) {
        return JOptionPane.showConfirmDialog(
                null, message, "Information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    /*
    Input pop-up for user input.
     */
    public static String displayInputInfo(String message) {
        return JOptionPane.showInputDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
