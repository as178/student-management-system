/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573)
 */
public class NavigationUtil {

    public static final String back = "b";
    public static final String exit = "x";

    private NavigationUtil() {}

    public static boolean backOrExit(String userInput) {
        if (exit.equalsIgnoreCase(userInput)) {
            ProgramShutdown.shutdown();
        } else if (back.equalsIgnoreCase(userInput)) {
            return true;
        }
        return false;
    }

    public static void checkExit(String userInput) {
        if (exit.equalsIgnoreCase(userInput)) {
            ProgramShutdown.shutdown();
        }
    }
}
