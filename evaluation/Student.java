package university.evaluation;

import university.people.Person;
import university.courses.Course;
import university.exceptions.InvalidMarkException;

import java.util.ArrayList;
import java.util.List;

public abstract class Student extends Person implements EvaluationPolicy {
    protected List<Course> enrolledCourses;

    public Student(String name, String id) {
        super(name, id);
        this.enrolledCourses = new ArrayList<>();
    }

    public void enrollCourse(Course course) {
        this.enrolledCourses.add(course);
    }

    public void setCourseGrade(String courseCode, double grade) throws InvalidMarkException {
        for (Course course : enrolledCourses) {
            if (course.getCourseCode().equals(courseCode)) {
                course.setGrade(grade);
                return;
            }
        }
        throw new IllegalArgumentException("Course with code " + courseCode + " not found for student " + getName());
    }

    public double calculateGPA() {
        double totalWeightedGPA = 0;
        int totalCredits = 0;
        for (Course course : enrolledCourses) {
            if (course.getGrade() != -1) { // Only consider graded courses
                totalWeightedGPA += course.getGPAScaleGrade() * course.getCredits();
                totalCredits += course.getCredits();
            }
        }
        if (totalCredits == 0) {
            return 0.0;
        }
        return totalWeightedGPA / totalCredits;
    }

    public String generateTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("\n--- Transcript for ").append(getName()).append(" (ID: ").append(id).append(") ---\n");
        if (enrolledCourses.isEmpty()) {
            transcript.append("No courses enrolled.\n");
        } else {
            for (Course course : enrolledCourses) {
                transcript.append(course.toString()).append("\n");
            }
        }
        transcript.append("Overall GPA: ").append(String.format("%.2f", calculateGPA())).append("\n");
        return transcript.toString();
    }

    // Abstract methods from EvaluationPolicy will be implemented by subclasses
}
