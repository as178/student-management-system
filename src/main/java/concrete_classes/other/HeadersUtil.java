/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 * 
 * This class has one method that is used everywhere throughout the
 * program. It wraps given sets of strings into a presentable header,
 * while also centering the text within the given length of 50.
 * It provides our program with aesthetic consistency.
 * 
 */
public final class HeadersUtil {

    private HeadersUtil() {}

    public static void printHeader(String... headerText) {
        int numberOfLines = 50;
        String lines = "=".repeat(numberOfLines);
        System.out.println(lines);
        
        for (String headerLine : headerText) {
            int headerLineLength = headerLine.length();
            int numberOfSpaces = (numberOfLines - headerLineLength) / 2;

            String spaces = " ".repeat(numberOfSpaces);
            System.out.println(spaces + headerLine + spaces);
        }

        System.out.println(lines);
    }
}