package university.courses;

import university.exceptions.InvalidMarkException;

public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private double grade;

    public Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.grade = -1; // -1 indicates no grade yet
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) throws InvalidMarkException {
        if (grade < 0 || grade > 100) {
            throw new InvalidMarkException("Grade must be between 0 and 100.");
        }
        this.grade = grade;
    }

    // Helper to convert percentage grade to GPA scale (e.g., 4.0 scale)
    public double getGPAScaleGrade() {
        if (grade == -1) return 0; // No grade yet
        if (grade >= 90) return 4.0;
        if (grade >= 80) return 3.0;
        if (grade >= 70) return 2.0;
        if (grade >= 60) return 1.0;
        return 0.0;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + credits + " credits) - Grade: " + (grade == -1 ? "N/A" : String.format("%.2f", grade));
    }
}
