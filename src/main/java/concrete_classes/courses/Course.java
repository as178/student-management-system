/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.courses;

/**
 *
 * @author Angela Saric (24237573)
 */
public class Course {

    private String courseId;
    private String courseName;
    private String courseMajor;
    private String coursePrerequisite;
    private int courseEstimatedHours;
    private String courseLecturer;
    private String courseDescription;

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
