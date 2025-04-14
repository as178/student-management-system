/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.file_input_output;

import abstract_classes.User;
import concrete_classes.courses.Course;
import concrete_classes.other.HeadersUtil;
import concrete_classes.students.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 *
 * @author Angela Saric (24237573)
 */
public class FilesManager {

    //using linked hashmap to keep the order of which the users are stored within the files
    public static LinkedHashMap<String, User> currentUsers;
    public static User currentUser;

    public static HashSet<Course> allCourses;

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

    public static void readAllCourses() {
        allCourses = new HashSet<Course>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/allCourses.txt"));

            while ((fileOutput = fileInput.readLine()) != null) {
                //splits only the first 6 commas
                String[] currentLine = fileOutput.split(",", 7);
                if (currentLine.length == 7) {
                    String courseId = currentLine[0];
                    String courseName = currentLine[1];
                    String courseMajor = currentLine[2];
                    String coursePrerequisite = currentLine[3];
                    int courseEstimatedHours = Integer.parseInt(currentLine[4]);
                    String courseLecturer = currentLine[5];
                    String courseDescription = currentLine[6];

                    Course newCourse = new Course(courseId, courseName, courseMajor, coursePrerequisite,
                            courseEstimatedHours, courseLecturer, courseDescription);

                    if (!allCourses.add(newCourse)) {
                        HeadersUtil.printHeader("Notification from reading allCourses.txt: ",
                                newCourse.getCourseId() + " wasn't added as it already", "exists in the course list.");
                    }
                }
            }
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from this file: allCourses.txt");
        }
    }

    public static void readEnrolledCourses(Student studentObj) {
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/studentsEnrolledCourses.txt"));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                if (currentLine[0].equals(String.valueOf(studentObj.getId()))) {
                    for (int i = 1; i < currentLine.length; i += 2) {

                        String courseCode = currentLine[i];
                        Float grade;

                        if (currentLine[i + 1].equals("null")) {
                            grade = null;
                        } else {
                            grade = Float.valueOf(currentLine[i + 1]);
                        }

                        studentObj.getEnrolledCourses().put(courseCode, grade);
                    }
                }
            }
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from this file: studentsEnrolledCourses.txt");
        }
    }

    public static void readPreviousCourses(Student studentObj) {
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/studentsPreviousCourses.txt"));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                if (currentLine[0].equals(String.valueOf(studentObj.getId()))) {
                    for (int i = 1; i < currentLine.length; i += 2) {

                        String courseCode = currentLine[i];
                        Float grade;

                        if (currentLine[i + 1].equals("null")) {
                            grade = null;
                        } else {
                            grade = Float.valueOf(currentLine[i + 1]);
                        }

                        studentObj.getPreviousCourses().put(courseCode, grade);
                    }
                }
            }
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from this file: studentsPreviousCourses.txt");
        }
    }
}