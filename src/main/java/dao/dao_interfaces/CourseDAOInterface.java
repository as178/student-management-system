/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.dao_interfaces;

import objects.Course;
import objects.Lecturer;
import objects.Student;
import java.util.HashMap;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * Interface implemented by CourseDAO.
 *
 */
public interface CourseDAOInterface {

    public Course getById(String id);

    public HashMap<String, Course> getAllCourses();
    
    public void readLecturerCourses(Lecturer lecturerObj);

    public void readStudentsCourses(Student currentStudent,
            HashMap<String, Float> studentsCourses, boolean previous);

    public void addCourseToTable(int studentId, String courseCode, Float grade, boolean previous);

    public void removeCourseFromTable(int studentId, String courseCode, boolean previous);

    public void updateEnrolledCourseTable(int studentId, String courseCode, Float newGrade);

    public HashMap<Integer, String> readEnrolledStudentsGrades(String courseCode);

    public void updateCourse(Course course);

    public void withdrawAllCourses(Student currentStudent);
}
