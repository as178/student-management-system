/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573)
 */
public final class ProgramShutdownUtil {

    private ProgramShutdownUtil() {}
    
    public static void shutdown() {
        HeadersUtil.printHeader("Thank you.", "Program shutting down . . .");
        System.exit(0);
    }
}
