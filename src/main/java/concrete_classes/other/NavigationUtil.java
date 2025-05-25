/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class has the following methods:
 *
 *
 */
public final class NavigationUtil {

    private static JFrame currentFrame;

    private NavigationUtil() {
    }

    public static void initialFrame(JFrame initialFrame) {
        currentFrame = initialFrame;
        currentFrame.setSize(700, 550);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.setVisible(true);
    }

    public static void newFrame(JFrame newFrame) {
        currentFrame.dispose();
        currentFrame = newFrame;
        currentFrame.setSize(700, 550);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.setVisible(true);
    }

    public static void exitProgram() {
        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to quit?",
                "Quit Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            ProgramShutdownUtil.shutdown();
        }
    }
}
