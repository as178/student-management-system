/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concrete_classes.lecturer;

import concrete_classes.courses.Course;
import concrete_classes.file_input_output.FilesManager;
import concrete_classes.other.HeadersUtil;
import concrete_classes.other.NavigationUtil;
import static concrete_classes.other.ValidationUtil.checkIntegerRange;
import concrete_classes.students.Student;
import interfaces.DashboardInterface;
import interfaces.HeaderInterface;
import interfaces.InputValidationInterface;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

/**
 *
 * @author williamniven
 */
public class LecturerEditStudentGrade implements DashboardInterface, HeaderInterface, InputValidationInterface {

    private Course currentCourse;
    private Student currentStudent;
    private int currentStudentGrade;
    HashMap<Integer, String> studentGrades;

    public LecturerEditStudentGrade(Course currentCourse, Student currentStudent, int currentStudentGrade, HashMap<Integer, String> studentGrades) {
        this.currentCourse = currentCourse;
        this.currentStudent = currentStudent;
        this.currentStudentGrade = currentStudentGrade;
        this.studentGrades = studentGrades;
    }

    @Override
    public void showMenu() {
        System.out.println("Current Grade: " + currentStudentGrade);
        System.out.println("s - sign off student\nb - Go Back (Enrolled Students)\nx - Exit");
    }

    @Override
    public void showHeader() {
        HeadersUtil.printHeader("Assign Student's Grade",
                "Set grade for the following student:",
                WordUtils.wrap(currentStudent.getId() + ", " + currentStudent.getFirstName() + " " + currentStudent.getLastName(), 46),
                "Please enter their new grade below.");
    }

    @Override
    public String validateUserInput() {

        Scanner scan = new Scanner(System.in);

        while (true) {
            this.showHeader();
            this.showMenu();
            String userInput = scan.nextLine();

            if (NavigationUtil.backOrExit(userInput)) {
                return "b";
            }

            if (userInput.trim().toLowerCase().equals("S")) {
                HeadersUtil.printHeader("Signing off",
                        currentStudent.getId() + " - " + currentStudent.getFirstName() + " - " + currentStudent.getLastName(),
                        "This will remove student from course");
                System.out.print("y - yes, remove student\nb - back\nx - exit");
                if (NavigationUtil.backOrExit(userInput)) {
                    return "b";
                }

                if (userInput.trim().toLowerCase().equals("s")) {
                    //couse id / grade
                    FilesManager.readCoursesFile(currentStudent, currentStudent.getEnrolledCourses(), FilesManager.studentsEnrolledCoursesFile);
                    //load previcouse cousersse
                    FilesManager.readCoursesFile(currentStudent, currentStudent.getPreviousCourses(), FilesManager.studentsPreviousCoursesFile);
                    //adds currentcourse and grade to pricouse courses hashmap
                    currentStudent.getPreviousCourses().put(currentCourse.getCourseId(), currentStudent.getEnrolledCourses().get(currentCourse.getCourseId()));
                    //remove from enrolled
                    currentStudent.getEnrolledCourses().remove(currentCourse.getCourseId());
                    //save enrolled 
                    FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsEnrolledCoursesFile, currentStudent.getEnrolledCourses(), false);
                    //save previous 
                    FilesManager.updateStudentCourseFile(currentStudent, FilesManager.studentsPreviousCoursesFile, currentStudent.getPreviousCourses(), true);
                }
            }

            if (checkIntegerRange(userInput, 0, 100)) {
                studentGrades.put(currentStudent.getId(), userInput);
                FilesManager.writeEnrolledStudentsGrades(currentStudent.getId(), currentCourse.getCourseId(), userInput);
                HeadersUtil.printHeader("Grade assigned successfully!");
                return "b";
            } else {
                HeadersUtil.printHeader("Please enter a number between 0 - 100.");
            }
        }
    }
}
