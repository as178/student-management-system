/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.file_input_output;

import abstract_classes.User;
import concrete_classes.courses.Course;
import concrete_classes.lecturer.Lecturer;
import concrete_classes.other.HeadersUtil;
import concrete_classes.students.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Angela Saric (24237573)
 */
public class FilesManager {

    //using linked hashmap to keep the order of which the users are stored within the files
    public static LinkedHashMap<String, User> currentUsers;
    public static User currentUser;

    public static HashSet<Course> allCourses;
    public static HashSet<String> allMajors;

    public static void readStudentsFile() {
        currentUsers = new LinkedHashMap<String, User>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/allStudents.txt"));

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
            System.out.println("Error in reading from this file: allStudents.txt");
        }
    }

    public static void writeStudentsFile() {
        try {
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter("src/main/java/text_files/allStudents.txt"));
            for (HashMap.Entry<String, User> userObj : currentUsers.entrySet()) {
                Student studentObj = (Student) userObj.getValue();
                fileOutput.write(studentObj.toString());
                fileOutput.newLine();
            }
            fileOutput.close();
        } catch (IOException e) {
            System.out.println("Error in writing to this file: allStudents.txt");
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

    public static void saveAllCourses() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("src/main/java/text_files/allCourses.txt"))) {
            for (Course course : allCourses) {
                String line = course.getCourseId() + ","
                        + course.getCourseName() + ","
                        + course.getCourseMajor() + ","
                        + course.getCoursePrerequisite() + ","
                        + course.getCourseEstimatedHours() + ","
                        + course.getCourseLecturer() + ","
                        + course.getCourseDescription();
                fileWriter.write(line);
                fileWriter.newLine();
            }
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Courses saved successfully!");
        } catch (IOException e) {
            HeadersUtil.printHeader("Error saving to file: allCourses.txt");
        }
    }
          
    public static void withdrawAllCourses(Student currentStudent) {
        for (String courseId : currentStudent.getEnrolledCourses().keySet()) {
            currentStudent.getPreviousCourses().put(courseId, -1f);
        }
        currentStudent.getEnrolledCourses().clear();

        updateStudentCourseFile(currentStudent,
                "src/main/java/text_files/studentsEnrolledCourses.txt",
                currentStudent.getEnrolledCourses(), false);
        updateStudentCourseFile(currentStudent,
                "src/main/java/text_files/studentsPreviousCourses.txt",
                currentStudent.getPreviousCourses(), true);
    }

    public static void updateStudentCourseFile(Student currentStudent, String path, HashMap<String, Float> studentsCourses, boolean append) {

        ArrayList<String> fileOutputLines = new ArrayList<String>();
        
        try {
            String fileOutput;
            BufferedReader fileInput = new BufferedReader(new FileReader(path));

            while ((fileOutput = fileInput.readLine()) != null) {
                String[] currentLine = fileOutput.split(",");

                if (currentLine[0].equals(String.valueOf(currentStudent.getId()))) {

                    StringBuilder newLine;
                    if (append) {
                        newLine = new StringBuilder(fileOutput);

                        HashSet<String> previousCourses = new HashSet<String>();
                        for (int i = 1; i < currentLine.length; i += 2) {
                            previousCourses.add(currentLine[i]);
                        }

                        for (HashMap.Entry<String, Float> currentCourse : studentsCourses.entrySet()) {
                            if (!previousCourses.contains(currentCourse.getKey())) {
                                newLine.append(",").append(currentCourse.getKey()).append(",").append(currentCourse.getValue());
                            }
                        }
                    } else {
                        newLine = new StringBuilder(currentStudent.getId() + "");
                        for (HashMap.Entry<String, Float> currentCourse : studentsCourses.entrySet()) {
                            newLine.append(",").append(currentCourse.getKey()).append(",").append(currentCourse.getValue());
                        }
                    }
                    fileOutputLines.add(newLine.toString());

                } else {
                    fileOutputLines.add(fileOutput);
                }
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in updating student's courses,", "in relation to file reading.");
            return;
        }

        try {
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter(path));

            for (String output : fileOutputLines) {
                fileOutput.write(output);
                fileOutput.newLine();
            }
            fileOutput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in updating student's courses,", "in relation to file writing.");
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

    public static HashMap<Integer, String> loadEnrolledStudentsGrades(String courseID) {
        HashMap<Integer, String> studentGrades = new HashMap<>();
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/studentsEnrolledCourses.txt"));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                for (int i = 0; i < currentLine.length; i++) {
                    if (currentLine[i].equals(courseID)) {
                        if (currentLine[i + 1].equals("-1")) {
                            studentGrades.put(Integer.parseInt(currentLine[0]), "Withdrawn");
                        } else if (currentLine[i + 1].equals("null")) {
                            studentGrades.put(Integer.parseInt(currentLine[0]), null);
                        } else {
                            studentGrades.put(Integer.parseInt(currentLine[0]), currentLine[i + 1]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from this file: studentsEnrolledCourses.txt");
        }
        return studentGrades;
    }

    public static void writeEnrolledStudentsGrades(int studentID, String courseId, String newGrade) {
        List<String> updatedLines = new ArrayList<>();
        String line;
        boolean updated = false;

        try (BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/studentsEnrolledCourses.txt"))) {
            while ((line = fileInput.readLine()) != null) {
                String[] currentLine = line.split(",");

                if (Integer.parseInt(currentLine[0]) == studentID) {
                    for (int i = 1; i < currentLine.length - 1; i += 2) {
                        if (currentLine[i].equals(courseId)) {
                            currentLine[i + 1] = newGrade;
                            updated = true;
                            break;
                        }
                    }
                }

                updatedLines.add(String.join(",", currentLine));
            }
        } catch (IOException e) {
            HeadersUtil.printHeader("Error reading from studentsEnrolledCourses.txt");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/text_files/studentsEnrolledCourses.txt"))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            HeadersUtil.printHeader("Error writing to studentsEnrolledCourses.txt");
            return;
        }

        if (!updated) {
            HeadersUtil.printHeader("No matching student-course pair found to update.");
        }
    }

    public static HashMap<Integer, Student> loadEnrolledStudents(HashMap<Integer, String> studentGrades) {
        HashMap<Integer, Student> enrolledStudents = new HashMap<>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/allStudents.txt"));
            while ((fileOutput = fileInput.readLine()) != null) {
                String[] currentLine = fileOutput.split(",");
                if (currentLine.length == 11) {

                    int studentId = Integer.parseInt(currentLine[0]);

                    if (studentGrades.containsKey(studentId)) {
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

                        enrolledStudents.put(Integer.parseInt(currentLine[0]), new Student(studentId, password, firstName, lastName, dateOfBirth,
                                personalEmail, uniEmail, phoneNumber, gender, address, major));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading from this file: allStudents.txt");
        }
        return enrolledStudents;
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

    public static void readAllMajors() {
        allMajors = new HashSet<String>();

        try (BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/allMajors.txt"))) {
            String fileOutput;
            while ((fileOutput = fileInput.readLine()) != null) {
                fileOutput = fileOutput.trim();

                if (!allMajors.add(fileOutput)) {
                    HeadersUtil.printHeader("Notification from reading allMajors.txt: ",
                            fileOutput + " wasn't added as it", "already exists in the majors list.");
                }
            }
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from this file: allMajors.txt");
        }
    }

    //Lectuer methods 
    public static void readLecturerFile() {
        currentUsers = new LinkedHashMap<String, User>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/allLecturers.txt"));

            while ((fileOutput = fileInput.readLine()) != null) {
                String[] currentLine = fileOutput.split(",");
                if (currentLine.length == 12) {
                    int lecturerId = Integer.parseInt(currentLine[0]);
                    String password = currentLine[1];
                    String firstName = currentLine[2];
                    String lastName = currentLine[3];
                    String dateOfBirth = currentLine[4];
                    String personalEmail = currentLine[5];
                    String uniEmail = currentLine[6];
                    String phoneNumber = currentLine[7];
                    Character gender = currentLine[8].charAt(0);
                    String address = currentLine[9];
                    String extention = currentLine[10];
                    String faculty = currentLine[11];

                    currentUsers.put(currentLine[0], new Lecturer(lecturerId, password, firstName, lastName, dateOfBirth,
                            personalEmail, uniEmail, phoneNumber, gender, address, extention, faculty));
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading from this file: allLecturers.txt");
        }
    }

    public static void writeLecturerFile() {
        try {
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter("src/main/java/text_files/allLecturers.txt"));
            for (HashMap.Entry<String, User> userObj : currentUsers.entrySet()) {
                Lecturer lecturerObj = (Lecturer) userObj.getValue();
                fileOutput.write(lecturerObj.toString());
                fileOutput.newLine();
            }
            fileOutput.close();
        } catch (IOException e) {
            System.out.println("Error in writing to this file: allLecturers.txt");
        }
    }

    public static void saveCurrentLecturer(Lecturer curreLecturer) {
        currentUsers.put(curreLecturer.getId() + "", curreLecturer);
        writeLecturerFile();
    }

    public static void readLecturerCourses(Lecturer lecturerObj) {
        String lineOutput;
        int index = 1;

        if (allCourses == null || allCourses.isEmpty()) {
            readAllCourses();
        }

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader("src/main/java/text_files/lecturerCourses.txt"));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                if (currentLine[0].trim().equals(String.valueOf(lecturerObj.getId()))) {
                    for (int i = 1; i < currentLine.length; i++) {

                        String courseCode = currentLine[i].trim();

                        for (Course course : allCourses) {
                            if (course.getCourseId().equals(courseCode)) {
                                lecturerObj.getCoursesTaught().put(index, course);
                                index++;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading from this file: LecturerCourses.txt");
        }
    }
}
