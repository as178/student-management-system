/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility_classes;

import model.DatabaseManager;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This utility class handles the full shutdown of the program;
 * closes the database connection beforehand.
 *
 */
public final class ProgramShutdownUtil {

    private ProgramShutdownUtil() {}

    public static void shutdown() {
        DatabaseManager.closeConnection();
        System.exit(0);
    }
}
