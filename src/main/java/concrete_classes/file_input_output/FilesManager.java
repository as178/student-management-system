/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.file_input_output;

import abstract_classes.User;
import objects.Admin;
import objects.Course;
import objects.Lecturer;
import concrete_classes.other.HeadersUtil;
import objects.Student;
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
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This is our file I/O class which handles all the program's files (reading &
 * writing). As was advised by Weihua, we have kept all methods for all users
 * within this one class to keep things centralized.
 *
 */
public class FilesManager {

    //static, final variables for keeping all the files' paths centralized
    public static final String allCoursesFile = "src/main/java/text_files/allCourses.txt";
    public static final String allLecturersFile = "src/main/java/text_files/allLecturers.txt";
    public static final String allMajorsFile = "src/main/java/text_files/allMajors.txt";
    public static final String allStudentsFile = "src/main/java/text_files/allStudents.txt";
    public static final String allAdminsFile = "src/main/java/text_files/allAdmins.txt";
    public static final String lecturerCoursesFile = "src/main/java/text_files/lecturerCourses.txt";
    public static final String studentsEnrolledCoursesFile = "src/main/java/text_files/studentsEnrolledCourses.txt";
    public static final String studentsPreviousCoursesFile = "src/main/java/text_files/studentsPreviousCourses.txt";

    /*
    The currentUsers can be all students, lecturers or admins read from their
    respective files, and this variable is used widely within our program.
    It is a linked hashmap to keep the order of which the users are stored
    within their files, for clarity while reading and writing. The currentUser
    represents the currently logged in student, lecturer or admin.
    
    The allCourses and allMajors hashset, as the names suggest, store all the
    courses and majors in the program, read from their respective files.
     */
    public static LinkedHashMap<String, User> currentUsers;
    public static User currentUser;

    public static HashSet<Course> allCourses;
    public static HashSet<String> allMajors;

    /*
    This method is primarily called when changes are made to users'
    personal details. It saves the currentUser within the currentUsers hashmap,
    and then calls on the writeAllUsers method to write what's in the hashmap onto
    the user's respective 'allUsers' file.
     */
    public static void saveCurrentUser(User currentUser) {
        currentUsers.put(currentUser.getId() + "", currentUser);
        //writeAllUsers(currentUser.getClass(), currentUser.getUsersPath());
    }

    /*
    This method can save any User or any class that extends User. It is only called
    in the saveCurrentUser method above, and takes the current user's class type as
    well as their 'allUsers' file path (String getUsersPath()).
     */
    public static <T extends User> void writeAllUsers(Class<T> userType, String path) {
        try {
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter(path));
            //iterate through the currentUsers hashmap
            for (HashMap.Entry<String, User> userObj : currentUsers.entrySet()) {
                //cast the users from the hashmap to the given, appropriate type of user e.g. Student
                T castedUser = userType.cast(userObj.getValue());
                //call the casted users' specialised toString method and write that output into the file
                fileOutput.write(castedUser.toString());
                //new line
                fileOutput.newLine();
            }
            fileOutput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in writing to given", "'allUsers' file.");
        }
    }

    //reads all students from the allStudents.txt file and saves them into the currentUsers hashmap
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

    //reads all courses from the allCourses.txt file and saves them into the allCourses hashset
    public static void readAllCourses() {
        allCourses = new HashSet<Course>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(allCoursesFile));

            while ((fileOutput = fileInput.readLine()) != null) {
                //splits only the first 6 commas in order to save course descriptions which may have commas in them
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

                    /*
                    add the read course into the hashset
                    - if the add is unsuccessful i.e. course is a duplicate ==> print a notification
                     */
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

    /*
    This method takes:
    - any student
    - their course list (either their enrolledCourses or previousCourses hashmap)
    - and the path to either studentsEnrolledCourses.txt or studentsPreviousCourses.txt
    
    It then reads whatever is in the given file, and populates the student's
    provided hashmap accordingly (saves the courseId, and the student's grade for that course).
    This way whatever is currently in the .txt files can be reflected in memory appropriately.
     */
    public static void readCoursesFile(Student studentObj, HashMap<String, Float> studentsCourses, String path) {
        String lineOutput;
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(path));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");
                //if we have reached the line with the student's id
                if (currentLine[0].equals(String.valueOf(studentObj.getId()))) {

                    //go through the entire line, skipping the student's id
                    for (int i = 1; i < currentLine.length; i += 2) {

                        //make the next index the courseId and . . .
                        String courseCode = currentLine[i];

                        //and the following index, the grade
                        // - if the grade in the file is "null", save grade as null
                        // - otherwise save the Float
                        Float grade;
                        grade = currentLine[i + 1].equals("null") ? null : Float.valueOf(currentLine[i + 1]);

                        //populate student's hashmap with the course and their grade for that course
                        studentsCourses.put(courseCode, grade);
                    }
                }
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in relation to reading", "from provided courses file.");
        }
    }

    /*
    When course information gets updated, this method is called to reflect the
    changes in the allCourses hashset to the allCourses.txt file.
     */
    public static void writeAllCourses() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(allCoursesFile))) {
            //iterate through hashset
            for (Course course : allCourses) {
                //call the course's toString method and write the output
                fileWriter.write(course.toString());
                //new line
                fileWriter.newLine();
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in writing to", "this file: allCourses.txt");
        }
    }

    /*
    When a student changes their major, this method is called to withdraw them from all
    their current courses, as they are only allowed to take courses from their new major.
     */
    public static void withdrawAllCourses(Student currentStudent) {
        /*
        iterate through the student's enrolledCourses hashmap
        and put all those courses into the student's previousCourses hashmap
        - set the grades for those courses to -1 to signalise withdrawal
         */
        for (String courseId : currentStudent.getEnrolledCourses().keySet()) {
            currentStudent.getPreviousCourses().put(courseId, -1f);
        }
        //afterwards, wipe the student's enrolledCourses hashmap
        currentStudent.getEnrolledCourses().clear();

        //update the changes in the hashmaps to their respective files (studentsEnrolledCourses.txt or studentsPreviousCourses.txt)
        updateStudentCourseFile(currentStudent, studentsEnrolledCoursesFile, currentStudent.getEnrolledCourses(), false);
        updateStudentCourseFile(currentStudent, studentsPreviousCoursesFile, currentStudent.getPreviousCourses(), true);
    }

    /*
    This method is used to update what's in a particular student's courses hashmap (enrolledCourses or previousCourses)
    to their respective .txt files (studentsEnrolledCourses.txt or studentsPreviousCourses.txt). It does both reading
    and writing of the files to make the logic flow more seamlessly.
     */
    public static void updateStudentCourseFile(Student currentStudent, String path, HashMap<String, Float> studentsCourses, boolean append) {

        //arraylist which will keep all the lines being read from the file
        //and will later be used to write back to the file
        ArrayList<String> fileOutputLines = new ArrayList<String>();

        try {
            String fileOutput;
            BufferedReader fileInput = new BufferedReader(new FileReader(path));

            while ((fileOutput = fileInput.readLine()) != null) {
                String[] currentLine = fileOutput.split(",");

                //if we have reached the line beginning with the student's id
                if (currentLine[0].equals(String.valueOf(currentStudent.getId()))) {

                    //we declare a newLine (StringBuilder used for efficiency)
                    StringBuilder newLine;

                    //if the given boolean append is set to true
                    if (append) {

                        //instansiate the newLine with what's already in the .txt file
                        newLine = new StringBuilder(fileOutput);

                        //make a hashset which will keep the courses already in the .txt file
                        HashSet<String> previousCourses = new HashSet<String>();

                        //populate the hashset with course ids only
                        //(skipping over the student's id, and the student's grades for those courses)
                        for (int i = 1; i < currentLine.length; i += 2) {
                            previousCourses.add(currentLine[i]);
                        }

                        //iterate through the student's given courses list (enrolledCourses or previousCourses)
                        for (HashMap.Entry<String, Float> currentCourse : studentsCourses.entrySet()) {

                            //if the courses in the student's list are not already in the .txt file
                            //append them to the newLine (there should be no inconcistensies with the courses
                            //the student takes in relation to what courses they have taken previously,
                            //however this is used as a precaution to mitigate such scenarios)
                            if (!previousCourses.contains(currentCourse.getKey())) {
                                newLine.append(",").append(currentCourse.getKey()).append(",").append(currentCourse.getValue());
                            }
                        }

                        //else if the given boolean append is set to false
                    } else {

                        //instansiate the newLine with the student's id
                        newLine = new StringBuilder(currentStudent.getId() + "");

                        //iterate through the student's given courses list (enrolledCourses or previousCourses)
                        for (HashMap.Entry<String, Float> currentCourse : studentsCourses.entrySet()) {
                            newLine.append(",").append(currentCourse.getKey()).append(",").append(currentCourse.getValue());
                        }
                    }

                    //after the newLine is built, add it to the arraylist
                    fileOutputLines.add(newLine.toString());

                    //otherwise, if the line doesn't start with the student's id, add the current line to the arraylist
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

            //iterate through the String arraylist which has all the lines
            //including the newLine for the student
            for (String output : fileOutputLines) {

                //write line to the .txt file (studentsEnrolledCourses.txt or studentsPreviousCourses.txt)
                fileOutput.write(output);
                //new line
                fileOutput.newLine();
            }
            fileOutput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in updating student's courses,", "in relation to file writing.");
        }
    }

    /*
    This method reads through the studentsEnrolledCourses.txt line by line to find all students' grades
    for a specific course. It returns a hashmap of students' ids and their grades.
    
    - if the grade is:
    > "-1.0" ==> the student is considered "Withdrawn"
    > "null" ==> the student hasn't be graded yet
    > otherwise the grade is saved as a String
     */
    public static HashMap<Integer, String> readEnrolledStudentsGrades(String courseID) {

        //hash map of student ids and their grades
        HashMap<Integer, String> studentGrades = new HashMap<>();
        String lineOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(studentsEnrolledCoursesFile));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");

                //read the entire line
                for (int i = 0; i < currentLine.length; i++) {

                    //if any section of the line has the specified course
                    if (currentLine[i].equals(courseID)) {

                        //save the grade of that student, for that course
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

        //return the hashmap
        return studentGrades;
    }

    /*
    Updates the grade for a specific student, in a course they are currently enrolled in,
    updating studentsEnrolledCourses.txt. This method reads each line of the file and looks
    for the line matching the provided student id. If the specified course id is found within
    that same line, its grade is updated to the new value provided. Afterwards, the updated
    content is written back to the file.
     */
    public static void writeEnrolledStudentsGrades(int studentID, String courseId, String newGrade) {

        //arraylist which will keep all the lines being read from the file
        //and will later be used to write back to the file
        List<String> updatedLines = new ArrayList<>();
        String line;
        boolean updated = false;

        try (BufferedReader fileInput = new BufferedReader(new FileReader(studentsEnrolledCoursesFile))) {
            while ((line = fileInput.readLine()) != null) {
                String[] currentLine = line.split(",");

                //if we have reached the line starting with the student's id
                if (Integer.parseInt(currentLine[0]) == studentID) {

                    //go through the entire line, just before the last grade
                    for (int i = 1; i < currentLine.length - 1; i += 2) {

                        //when the index reaches the provided course id
                        if (currentLine[i].equals(courseId)) {

                            //update the student's grade for that course with the one provided
                            currentLine[i + 1] = newGrade;

                            //confirm update
                            updated = true;
                            break;
                        }
                    }
                }

                //add the updated line back to the arraylist
                updatedLines.add(String.join(",", currentLine));
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: studentsEnrolledCourses.txt");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentsEnrolledCoursesFile))) {

            //iterate through the arraylist
            for (String updatedLine : updatedLines) {
                //write the updated line to the file
                writer.write(updatedLine);
                //new line
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in writing to", "this file: studentsEnrolledCourses.txt");
            return;
        }

        //if no update was confirmed, print a notification
        if (!updated) {
            HeadersUtil.printHeader("No matching student-course", "pair found to update.");
        }
    }

    /*
    Simple method which reads through the allMajors.txt file and saves each major into
    the allMajors hashset.
     */
    public static void readAllMajors() {
        allMajors = new HashSet<String>();

        try (BufferedReader fileInput = new BufferedReader(new FileReader(allMajorsFile))) {
            String fileOutput;
            while ((fileOutput = fileInput.readLine()) != null) {
                fileOutput = fileOutput.trim();

                //if the major being added is already in the hashset (unsuccessful add)
                //print a notification 
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

    /*
    This method reads all lecturers from the allLecturers.txt file and puts them
    into the currentUsers hashmap.
     */
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

    /*
    This method reads the lecturerCourses.txt file for the line starting with the lecturer's id,
    then maps each course id in that line to a course object (if it exists in the allCourses hashset) and
    adds it to the lecturer's coursesTaught hashmap.
     */
    public static void readLecturerCourses(Lecturer lecturerObj) {
        String lineOutput;
        //separate index included for future menu operations
        int index = 1;

        //if the hashset hasn't been loaded yet or is somehow empty
        if (allCourses == null || allCourses.isEmpty()) {
            readAllCourses();
        }

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(lecturerCoursesFile));
            while ((lineOutput = fileInput.readLine()) != null) {
                String[] currentLine = lineOutput.split(",");

                //if the current line starts with the lecturer's id
                if (currentLine[0].trim().equals(String.valueOf(lecturerObj.getId()))) {

                    //read the entire line (skipping the lecturer id)
                    for (int i = 1; i < currentLine.length; i++) {

                        //get the course id 
                        String courseCode = currentLine[i].trim();

                        for (Course course : allCourses) {
                            //and if that course id exists in the allCourses hashset
                            if (course.getCourseId().equals(courseCode)) {

                                //put the index and corresponding course object into 
                                //the lecturer's coursesTaught hashmap
                                //lecturerObj.getCoursesTaught().put(index, course);

                                //increase the index
                                index++;

                                //break loop once course is found and correctly mapped
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

    /*
    This method reads all admins from the allAdmins.txt file and puts them
    into the currentUsers hashmap.
     */
    public static void readAllAdmins() {
        currentUsers = new LinkedHashMap<String, User>();
        String fileOutput;

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(allAdminsFile));

            while ((fileOutput = fileInput.readLine()) != null) {
                String[] currentLine = fileOutput.split(",");
                if (currentLine.length == 10) {
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

                    currentUsers.put(currentLine[0], new Admin(lecturerId, password, firstName, lastName, dateOfBirth,
                            personalEmail, uniEmail, phoneNumber, gender, address));
                }
            }
            fileInput.close();
        } catch (IOException e) {
            HeadersUtil.printHeader("Error in reading from", "this file: allAdmins.txt");
        }
    }
}
