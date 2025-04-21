/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 * 
 * This class has two methods:
 * 
 * - backOrExit, checks whether the user picked the option to
 *   go back or exit the program entirely; boolean return
 * 
 * - checkExit, only checks if the user picked to exit the program;
 *   calls ProgramShutdownUtil directly
 * 
 *  Both methods call on the ProgramShutdownUtil when required,
 *  which handles the program shutdown separately from this class.
 * 
 */
public final class NavigationUtil {

    //static, final Strings which determine what the user will pick
    //to go back or exit the program
    private static final String back = "b";
    private static final String exit = "x";

    private NavigationUtil() {}

    public static boolean backOrExit(String userInput) {
        if (exit.equalsIgnoreCase(userInput)) {
            ProgramShutdownUtil.shutdown(); 
        } else if (back.equalsIgnoreCase(userInput)) {
            return true;
        }
        return false;
    }

    public static void checkExit(String userInput) {
        if (exit.equalsIgnoreCase(userInput)) {
            ProgramShutdownUtil.shutdown();
        }
    }
}
