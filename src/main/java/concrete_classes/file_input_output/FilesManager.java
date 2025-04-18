/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.file_input_output;

import abstract_classes.User;
import concrete_classes.courses.Course;
import concrete_classes.lecturer.Lecturer;
import concrete_classes.other.HeadersUtil;
import concrete_classes.student.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author Angela Saric (24237573)
 */
public class FilesManager {

    public static final String allCoursesFile = "src/main/java/text_files/allCourses.txt";
    public static final String allLecturersFile = "src/main/java/text_files/allLecturers.txt";
    public static final String allMajorsFile = "src/main/java/text_files/allMajors.txt";
    public static final String allStudentsFile = "src/main/java/text_files/allStudents.txt";
    public static final String lecturerCoursesFile = "src/main/java/text_files/lecturerCourses.txt";
    public static final String studentsEnrolledCoursesFile = "src/main/java/text_files/studentsEnrolledCourses.txt";
    public static final String studentsPreviousCoursesFile = "src/main/java/text_files/studentsPreviousCourses.txt";

    //using linked hashmap to keep the order of which the users are stored within the files
    public static LinkedHashMap<String, User> currentUsers;
    public static User currentUser;

    public static LinkedHashSet<Course> allCourses;
    public static LinkedHashSet<String> allMajors;

    public static void saveCurrentUser(User currentUser) {
        currentUsers.put(currentUser.getId() + "", currentUser);
        writeAllUsers(currentUser.getClass(), currentUser.getUsersPath());
    }

    public static <T extends User> void writeAllUsers(Class<T> userType, String path) {
        try {
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter(path));
            for (HashMap.Entry<String, User> userObj : currentUsers.entrySet()) {
                T castedUser = userType.cast(userObj.getValue());
                fileOutput.write(castedUser.toString());
                fileOutput.newLine();
            }
            fileOutput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in writing to given", "'allUsers' file.");
        }
    }

    public static void readAllStudents() {
        currentUsers = new LinkedHashMap<String, User>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(allStudentsFile));

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

            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: allStudents.txt");
        }
    }

    public static void readAllCourses() {
        allCourses = new LinkedHashSet<Course>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(allCoursesFile));

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
                        HeadersUtil.printHeader("Notification from reading allCourses.txt:",
                                newCourse.getCourseId() + " wasn't added as it already", "exists in the course list.");
                    }
                }
            }

            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: allCourses.txt");
        }
    }

    // Used for reading both studentsEnrolledCourses.txt & studentsPreviousCourses.txt
    public static void readCoursesFile(Student studentObj, HashMap<String, Float> studentsCourses, String path) {
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(path));
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

                        studentsCourses.put(courseCode, grade);
                    }
                }
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in relation to reading", "from provided courses file.");
        }
    }

    public static void writeAllCourses() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(allCoursesFile))) {
            for (Course course : allCourses) {
                StringBuilder newLine = new StringBuilder();
                newLine.append(course.getCourseId()).append(",")
                        .append(course.getCourseName()).append(",")
                        .append(course.getCourseMajor()).append(",")
                        .append(course.getCoursePrerequisite()).append(",")
                        .append(course.getCourseEstimatedHours()).append(",")
                        .append(course.getCourseLecturer()).append(",")
                        .append(course.getCourseDescription());

                fileWriter.write(newLine.toString());
                fileWriter.newLine();
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in writing to", "this file: allCourses.txt");
        }
    }

    public static void withdrawAllCourses(Student currentStudent) {
        for (String courseId : currentStudent.getEnrolledCourses().keySet()) {
            currentStudent.getPreviousCourses().put(courseId, -1f);
        }
        currentStudent.getEnrolledCourses().clear();

        updateStudentCourseFile(currentStudent, studentsEnrolledCoursesFile, currentStudent.getEnrolledCourses(), false);
        updateStudentCourseFile(currentStudent, studentsPreviousCoursesFile, currentStudent.getPreviousCourses(), true);
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

    public static HashMap<Integer, String> readEnrolledStudentsGrades(String courseID) {
        HashMap<Integer, String> studentGrades = new HashMap<>();
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(studentsEnrolledCoursesFile));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                for (int i = 0; i < currentLine.length; i++) {
                    if (currentLine[i].equals(courseID)) {
                        if (currentLine[i + 1].equals("-1.0")) {
                            studentGrades.put(Integer.parseInt(currentLine[0]), "Withdrawn");
                        } else if (currentLine[i + 1].equals("null")) {
                            studentGrades.put(Integer.parseInt(currentLine[0]), null);
                        } else {
                            studentGrades.put(Integer.parseInt(currentLine[0]), currentLine[i + 1]);
                        }
                    }
                }
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: studentsEnrolledCourses.txt");
        }
        return studentGrades;
    }

    public static void writeEnrolledStudentsGrades(int studentID, String courseId, String newGrade) {
        List<String> updatedLines = new ArrayList<>();
        String line;
        boolean updated = false;

        try (BufferedReader fileInput = new BufferedReader(new FileReader(studentsEnrolledCoursesFile))) {
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
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: studentsEnrolledCourses.txt");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentsEnrolledCoursesFile))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in writing to", "this file: studentsEnrolledCourses.txt");
            return;
        }

        if (!updated) {
            HeadersUtil.printHeader("No matching student-course", "pair found to update.");
        }
    }

    public static void readAllMajors() {
        allMajors = new LinkedHashSet<String>();

        try (BufferedReader fileInput = new BufferedReader(new FileReader(allMajorsFile))) {
            String fileOutput;
            while ((fileOutput = fileInput.readLine()) != null) {
                fileOutput = fileOutput.trim();

                if (!allMajors.add(fileOutput)) {
                    HeadersUtil.printHeader("Notification from reading allMajors.txt:",
                            fileOutput + " wasn't added as it", "already exists in the majors list.");
                }
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: allMajors.txt");
        }
    }

    //Lecturer methods 
    public static void readAllLecturers() {
        currentUsers = new LinkedHashMap<String, User>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(allLecturersFile));

            while ((fileOutput = fileInput.readLine()) != null) {
                String[] currentLine = fileOutput.split(",");
                if (currentLine.length == 11) {
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
                    String faculty = currentLine[10];

                    currentUsers.put(currentLine[0], new Lecturer(lecturerId, password, firstName, lastName, dateOfBirth,
                            personalEmail, uniEmail, phoneNumber, gender, address, faculty));
                }
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: allLecturers.txt");
        }
    }

    public static void readLecturerCourses(Lecturer lecturerObj) {
        String lineOutput;
        int index = 1;

        if (allCourses == null || allCourses.isEmpty()) {
            readAllCourses();
        }

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(lecturerCoursesFile));
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
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: lecturerCourses.txt");
        }
    }
}
