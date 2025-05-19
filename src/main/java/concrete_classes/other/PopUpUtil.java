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
 * This class has one method that is used throughout the program to provide
 * small pop-ups with custom messages depending on the context.
 *
 */
public final class PopUpUtil {

    private PopUpUtil() {}

    //can add parent component for centering later
    public static void displayInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void displayError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void displayWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
