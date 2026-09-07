package university.courses;

import university.evaluation.Student;
import university.exceptions.InvalidMarkException;

import java.util.List;

public class ManagementStudent extends Student {

    public ManagementStudent(String name, String id) {
        super(name, id);
    }

    @Override
    public double calculateGrade() {
        if (enrolledCourses == null || enrolledCourses.isEmpty()) {
            return 0.0;
        }

        double totalWeightedGrade = 0;
        int totalCredits = 0;

        // For simplicity, let's assume a weighted average where each course grade is weighted by its credits.
        // This is a common approach for GPA calculation, but here we'll use it for the overall 'grade'.
        for (Course course : enrolledCourses) {
            if (course.getGrade() != -1) { // Only consider graded courses
                totalWeightedGrade += course.getGrade() * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        if (totalCredits == 0) {
            return 0.0;
        }
        return totalWeightedGrade / totalCredits;
    }

    @Override
    public String generateReport() {
        return "Management Student: " + getName() + " (ID: " + id + ") - Weighted Course Grade: " + String.format("%.2f", calculateGrade()) + " | GPA: " + String.format("%.2f", calculateGPA());
    }
}
