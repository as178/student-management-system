/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.other;

/**
 *
 * @author Angela Saric (24237573)
 */
public final class GradesUtil {

    private GradesUtil() {}

    public static String convertFloatToGrade(Float grade) {
        if (grade == null) {
            return "N/A";
        } else if (grade == -1) {
            return "Withdrawn";
        } else if (grade >= 89.5f && grade <= 100.0f) {
            return "A+";
        } else if (grade >= 84.5f && grade < 89.5f) {
            return "A";
        } else if (grade >= 79.5f && grade < 84.5f) {
            return "A-";
        } else if (grade >= 74.5f && grade < 79.5f) {
            return "B+";
        } else if (grade >= 69.5f && grade < 74.5f) {
            return "B";
        } else if (grade >= 64.5f && grade < 69.5f) {
            return "B-";
        } else if (grade >= 59.5f && grade < 64.5f) {
            return "C+";
        } else if (grade >= 54.5f && grade < 59.5f) {
            return "C";
        } else if (grade >= 49.5f && grade < 54.5f) {
            return "C-";
        } else if (grade >= 0.0f && grade < 49.5f) {
            return "D";
        } else {
            return "Invalid";
        }
    }
    
        public static int convertFloatToGPA(Float grade) {
        if (grade >= 89.5f && grade <= 100.0f) {
            return 9; //A+
        } else if (grade >= 84.5f && grade < 89.5f) {
            return 8; //A
        } else if (grade >= 79.5f && grade < 84.5f) {
            return 7; //A-
        } else if (grade >= 74.5f && grade < 79.5f) {
            return 6; //B+
        } else if (grade >= 69.5f && grade < 74.5f) {
            return 5; //B
        } else if (grade >= 64.5f && grade < 69.5f) {
            return 4; //B-
        } else if (grade >= 59.5f && grade < 64.5f) {
            return 3; //C+
        } else if (grade >= 54.5f && grade < 59.5f) {
            return 2; //C
        } else if (grade >= 49.5f && grade < 54.5f) {
            return 1; //C-
        } else if (grade >= 0.0f && grade < 49.5f) {
            return 0; //D
        } else {
            System.out.println("invalid grade score to GPA");
            return 1; //So it dose not impact GPA score
        }
    }
}
