package university.courses;

import university.evaluation.Student;
import university.exceptions.InvalidMarkException;

import java.util.List;

public class EngineeringStudent extends Student {

    public EngineeringStudent(String name, String id) {
        super(name, id);
    }

    @Override
    public double calculateGrade() {
        if (enrolledCourses == null || enrolledCourses.isEmpty()) {
            return 0.0;
        }
        double sumOfGrades = 0;
        int gradedCoursesCount = 0;
        for (Course course : enrolledCourses) {
            if (course.getGrade() != -1) { // Only consider graded courses
                sumOfGrades += course.getGrade();
                gradedCoursesCount++;
            }
        }
        if (gradedCoursesCount == 0) {
            return 0.0;
        }
        return sumOfGrades / gradedCoursesCount;
    }

    @Override
    public String generateReport() {
        return "Engineering Student: " + getName() + " (ID: " + id + ") - Average Course Grade: " + String.format("%.2f", calculateGrade()) + " | GPA: " + String.format("%.2f", calculateGPA());
    }
}
