/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.courses;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 * 
 * This is the course class which holds all attributes regarding
 * courses, as well as their respective get/set methods. All courses
 * read from the "allCourses.txt" file are stored in a
 * "public static HashSet<Course> allCourses" in the FilesManager,
 * and thus the "equals" & "hashcode" methods have been overridden
 * to compare courses based on their unique "courseId" identifiers.
 * 
 */
public class Course {

    protected String courseId;
    protected String courseName;
    protected String courseMajor;
    protected String coursePrerequisite;
    protected int courseEstimatedHours;
    protected String courseLecturer;
    protected String courseDescription;

    public Course(String courseId, String courseName, String courseMajor, String coursePrerequisite,
            int courseEstimatedHours, String courseLecturer, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseMajor = courseMajor;
        this.coursePrerequisite = coursePrerequisite;
        this.courseEstimatedHours = courseEstimatedHours;
        this.courseLecturer = courseLecturer;
        this.courseDescription = courseDescription;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public String getCourseMajor() {
        return this.courseMajor;
    }

    public String getCoursePrerequisite() {
        return this.coursePrerequisite;
    }

    public int getCourseEstimatedHours() {
        return this.courseEstimatedHours;
    }

    public String getCourseLecturer() {
        return this.courseLecturer;
    }

    public String getCourseDescription() {
        return this.courseDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Course)) {
            return false;
        }
        Course otherCourse = (Course) o;
        return this.courseId.equals(otherCourse.courseId);
    }

    @Override
    public int hashCode() {
        return courseId.hashCode();
    }
}