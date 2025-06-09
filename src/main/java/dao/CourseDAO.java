/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import objects.Course;
import objects.Lecturer;
import utility_classes.PopUpUtil;
import objects.Student;
import dao.dao_interfaces.CourseDAOInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import model.DatabaseManager;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This Data Access Object is responsible for any Course related queries. It is
 * derived from out original FilesManager class.
 *
 */
public class CourseDAO implements CourseDAOInterface {

    private Connection currentConnection = DatabaseManager.getCurrentConnection();

    /*
    Method to get Course by input of ID.
     */
    @Override
    public Course getById(String id) {
        String sqlStatement = "SELECT * FROM Course WHERE id = ?";
        Course course = null;

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                course = new Course(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("major"),
                        rs.getString("prerequisite_id"),
                        rs.getInt("estimated_hours"),
                        rs.getString("lecturer_id"),
                        rs.getString("description")
                );
            }
        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading all courses from the Course table.");
        }

        return course;
    }

    /*
    This method returns all courses from the course table.
    A simple HashMap is used to store these courses and their
    course codes for efficient lookup.
     */
    @Override
    public HashMap<String, Course> getAllCourses() {
        HashMap<String, Course> allCourses = new HashMap<String, Course>();
        String sqlStatement = "SELECT * FROM Course";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Course newCourse = new Course(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("major"),
                        rs.getString("prerequisite_id"),
                        rs.getInt("estimated_hours"),
                        rs.getString("lecturer_id"),
                        rs.getString("description")
                );

                allCourses.put(newCourse.getCourseId(), newCourse);
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading all courses from the Course table.");
        }

        return allCourses;
    }

    /*
    This method reads the Course table for courses with a particular lecturer's id
    and populates that lecturer's coursesTaught hashmap; i.e. it retrieves
    the courses a particular lecturer teaches.
     */
    @Override
    public void readLecturerCourses(Lecturer lecturerObj) {
        HashMap<String, Course> allCourses = this.getAllCourses();
        String sqlStatement = "SELECT id FROM Course WHERE lecturer_id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, lecturerObj.getId());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String courseId = rs.getString("id");
                Course taughtCourse = allCourses.get(courseId);
                lecturerObj.getCoursesTaught().put(courseId, taughtCourse);
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading lecturer's courses from the Course table.");
        }
    }

    /*
    This method reads whatever is in the respective table (PreviousCourse or EnrolledCourse),
    and populates a given student's hashmap accordingly (saves the courseId, and the student's
    grade for that course).
    
    It does so by taking the currently logged in student, their courses hashmap (current enrollments or pevious),
    and a boolean for choosing which table to update from. It iterates through the rows which have the student's id,
    and stores their course id and grade for the course into the provided hashmap.
    
    It will initially clear the hashmap in case there is no entries within the table.
     */
    @Override
    public void readStudentsCourses(Student currentStudent, HashMap<String, Float> studentsCourses, boolean previous) {
        String tableName = previous ? "PreviousCourse" : "EnrolledCourse";
        String sqlStatement = "SELECT course_id, grade FROM " + tableName + " WHERE student_id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, currentStudent.getId());
            ResultSet rs = preparedStatement.executeQuery();

            studentsCourses.clear();

            while (rs.next()) {
                String courseId = rs.getString("course_id");
                Float grade = rs.getObject("grade", Float.class); //retrieving object in case grade is null
                studentsCourses.put(courseId, grade);
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading student's courses from the " + tableName + " table.");
        }
    }

    /*
    Method to add a course to EnrolledCourse/PreviousCourse for a particular
    student, depending on the needs/context of the program.; e.g. a student could 
    be enrolling into a new course, EnrolledCourse table addition, or perhaps they withdrew
    from a course which needs to then be added to the PreviousCourse table . . .
     */
    @Override
    public void addCourseToTable(int studentId, String courseId, Float grade, boolean previous) {
        String tableName = previous ? "PreviousCourse" : "EnrolledCourse";
        String sqlStatement = "INSERT INTO " + tableName + " (student_id, course_id, grade) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseId);
            if (grade == null) {
                preparedStatement.setNull(3, Types.FLOAT); //null case
            } else {
                preparedStatement.setFloat(3, grade); //regular grade 
            }

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in adding course to the " + tableName + " table.");
        }
    }

    /*
    Method to remove a course from EnrolledCourse/PreviousCourse for a particular
    student, depending on the needs/context of the program.
     */
    @Override
    public void removeCourseFromTable(int studentId, String courseId, boolean previous) {
        String tableName = previous ? "PreviousCourse" : "EnrolledCourse";
        String sqlStatement = "DELETE FROM " + tableName + " WHERE student_id = ? AND course_id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseId);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in removing course from the " + tableName + " table.");
        }
    }

    /*
    Method to update a grade for a particular student, in a
    particular course within the EnrolledCourse table.
     */
    @Override
    public void updateEnrolledCourseTable(int studentId, String courseId, Float newGrade) {

        String sqlStatement = "UPDATE EnrolledCourse SET grade = ? WHERE student_id = ? AND course_id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setFloat(1, newGrade);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setString(3, courseId);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in updating grade within EnrolledCourse table.");
        }
    }

    /*
    This method iterates through the EnrolledCourse table to find all students' grades
    for a specific course. It returns a hashmap of students' ids and their grades.
    
    - if the grade is:
    > "null" ==> the student hasn't been graded yet
    > otherwise the grade is saved as a Float
     */
    @Override
    public HashMap<Integer, Float> readEnrolledStudentsGrades(String courseId) {
        HashMap<Integer, Float> studentGrades = new HashMap<>();
        String sqlStatement = "SELECT student_id, grade FROM EnrolledCourse WHERE course_id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, courseId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                Float grade = rs.getObject("grade", Float.class);

                if (grade == null) {
                    studentGrades.put(studentId, null);
                } else {
                    studentGrades.put(studentId, grade);
                }
            }
        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in reading grades for students within EnrolledCourse table.");
        }

        return studentGrades;
    }

    /*
    This method updates a course within the database when its 
    information is changed.
     */
    @Override
    public void updateCourse(Course course) {
        String sqlStatement = "UPDATE Course SET name = ?, major = ?, prerequisite_id = ?, estimated_hours = ?,"
                + "lecturer_id = ?, description = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseMajor());
            preparedStatement.setString(3, course.getCoursePrerequisite());
            preparedStatement.setInt(4, course.getCourseEstimatedHours());
            preparedStatement.setString(5, course.getCourseLecturer());
            preparedStatement.setString(6, course.getCourseDescription());
            preparedStatement.setString(7, course.getCourseId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in updating " + course.getCourseId() + " in the Course table.");
            return;
        }

        PopUpUtil.displayInfo("Course updated successfully!");
    }

    /*
    When a student changes their major, this method is called to withdraw them from all
    their current courses, as they are only allowed to take courses from their new major.
     */
    @Override
    public void withdrawAllCourses(Student currentStudent) {
        HashMap<String, Float> enrolledCourses = currentStudent.getEnrolledCourses();

        //iterate thru student's list of current enrollments
        for (String courseId : enrolledCourses.keySet()) {

            //place enrolled course into PreviousCourse table with -1.0 grade (withdrawn)
            this.addCourseToTable(currentStudent.getId(), courseId, -1f, true);

            //remove that same course from EnrolledCourse table
            this.removeCourseFromTable(currentStudent.getId(), courseId, false);
        }
    }

    /*
    Method (used during testing) for clearing out students' courses;
    giving a clean state work with.
     */
    @Override
    public void removeStudentCourses(Student currentStudent, boolean previous) {

        String tableName = previous ? "PreviousCourse" : "EnrolledCourse";
        String sqlStatement = "DELETE FROM " + tableName + " WHERE student_id = ?";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, currentStudent.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            PopUpUtil.displayError("Error in erasing courses from the " + tableName + " table.");
            return;
        }

        PopUpUtil.displayInfo("Courses cleared successfully!");
    }
}
