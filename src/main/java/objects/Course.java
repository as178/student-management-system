/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 * 
 * This is the course class which holds all attributes regarding
 * courses, as well as their respective get/set methods.
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
    
    public void setCourseName(String newName){
        this.courseName = newName;
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
    
    public void setCourseEstimatedHours(int estimatedHours) {
        this.courseEstimatedHours = estimatedHours;
    }

    public String getCourseLecturer() {
        return this.courseLecturer;
    }

    public String getCourseDescription() {
        return this.courseDescription;
    }
    
    public void setCourseDescription(String newDescription){
        this.courseDescription = newDescription;
    }
}