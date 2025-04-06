/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.file_input_output;

import abstract_classes.User;
import concrete_classes.components.Student;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573)
 */
public class FilesManager {

    public static HashMap<String, User> currentUsers;
    public static User currentUser;

    public static void readStudentsFile() {
        currentUsers = new HashMap<String, User>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/students.txt"));

            while ((fileOutput = fileInput.readLine()) != null) {
                String[] currentLine = fileOutput.split(",");
                if (currentLine.length == 11) {
                    int studentId = Integer.parseInt(currentLine[0]);
                    String password = currentLine[1];
                    String firstName = currentLine[2];
                    String lastName = currentLine[3];
                    String dateOfBirth = currentLine[4];
                    String personalEmail = currentLine[5];
                    String uniEmail = currentLine[6];
                    String phoneNumber = currentLine[7];
                    Character gender = currentLine[8].charAt(0);
                    String address = currentLine[9];
                    String major = currentLine[10];
                    currentUsers.put(currentLine[0], new Student(studentId, password, firstName, lastName, dateOfBirth,
                            personalEmail, uniEmail, phoneNumber, gender, address, major));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading from this file: students.txt");
        }
    }
}