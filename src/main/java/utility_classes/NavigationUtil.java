/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility_classes;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This utility class keeps track of the currently opened frame within the
 * program. It is the core component in ensuring the user is able to initialise
 * the program, go back and forth between dashboards/frames, and exit the
 * program gracefully.
 *
 */
public final class NavigationUtil {

    //public so test cases can set frames
    public static JFrame currentFrame;
    //testmode to setvisibiliy when tests are run, stops gui from being visiable
    public static boolean testMode = false;

    private NavigationUtil() {
    }

    /*
    Called at the beginning of the program to set up 
    the first frame.
     */
    public static void initialFrame(JFrame initialFrame) {
        currentFrame = initialFrame;
        currentFrame.setSize(700, 550); //constant frame size
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit on close
        currentFrame.setLocationRelativeTo(null); //window centred 
        currentFrame.setVisible(true); //make the frame visible
    }

    /*
    Called any time the user wishes to switch to a different
    frame/dashboard or if a refresh is required. The currentFrame
    is always disposed of before the new one is configured to
    ensure only one dashboard is opened at a time.
     */
    public static void newFrame(JFrame newFrame) {
        if (currentFrame != null) {
            currentFrame.dispose(); // Prevent NullPointerException
        }

        currentFrame = newFrame;
        currentFrame.setSize(700, 550);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentFrame.setLocationRelativeTo(null);
        
        if (!testMode) {
            currentFrame.setVisible(true);
        }
    }

    /*
    This method confirms the user wants to exit the program,
    and if so, calls on the ProgramShutdownUtil.shutdown()
    method which handles the shutdown.
     */
    public static void exitProgram() {
        int confirmation = PopUpUtil.displayConfirmInfo("Are you sure you want to quit?");
        if (confirmation == JOptionPane.YES_OPTION) {
            ProgramShutdownUtil.shutdown();
        }
    }
}
