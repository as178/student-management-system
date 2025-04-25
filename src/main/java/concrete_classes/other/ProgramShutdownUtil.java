/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 * 
 * This class handles the full shutdown of the program;
 * prior to shutdown it prints out a message to let the user
 * know the program will terminate.
 * 
 */
public final class ProgramShutdownUtil {

    private ProgramShutdownUtil() {}
    
    public static void shutdown() {
        HeadersUtil.printHeader("Thank you.", "Program shutting down . . .");
        System.exit(0);
    }
}
