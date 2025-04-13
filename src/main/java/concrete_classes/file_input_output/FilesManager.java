/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.file_input_output;

import abstract_classes.User;
import concrete_classes.students.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Angela Saric (24237573)
 */
public class FilesManager {

    //using linked hashmap to keep the order of which the users are stored within the files
    public static LinkedHashMap<String, User> currentUsers;
    public static User currentUser;

    public static void readStudentsFile() {
        currentUsers = new LinkedHashMap<String, User>();
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

    public static void writeStudentsFile() {
        try {
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter("src/main/java/text_files/students.txt"));
            for (HashMap.Entry<String, User> userObj : currentUsers.entrySet()) {
                Student studentObj = (Student) userObj.getValue();
                fileOutput.write(studentObj.toString());
                fileOutput.newLine();
            }
            fileOutput.close();
        } catch (IOException e) {
            System.out.println("Error in writing to this file: students.txt");
        }
    }

    public static void saveCurrentStudent(Student currentStudent) {
        currentUsers.put(currentStudent.getId() + "", currentStudent);
        writeStudentsFile();
    }

    public static void loadEnrolledCourses(Student studentObj) {
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/studentsEnrolledCourses"));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                if (currentLine[0].equals(String.valueOf(studentObj.getId()))) {
                    studentObj.getEnrolledCourses().put(currentLine[1], Float.valueOf(currentLine[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading from this file: studentsEnrolledCourses.txt");
        }
    }

    public static void loadCompletedCourses(Student studentObj) {
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/studentsCompletedCourses"));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                if (currentLine[0].equals(String.valueOf(studentObj.getId()))) {
                    studentObj.getCompletedCourses().put(currentLine[1], Float.valueOf(currentLine[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading from this file: studentsCompletedCourses.txt");
        }
    }
}