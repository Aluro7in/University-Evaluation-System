package university.app;

import university.evaluation.EvaluationPolicy;
import university.courses.EngineeringStudent;
import university.courses.ManagementStudent;
import university.people.Person;
import university.courses.Course;
import university.exceptions.InvalidMarkException;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        // Create student objects using EvaluationPolicy reference
        EngineeringStudent engStudent = new EngineeringStudent("Alice Smith", "ENG001");
        ManagementStudent mgmtStudent = new ManagementStudent("Bob Johnson", "MGT002");

        // Enroll courses and set grades for Engineering Student
        Course engCourse1 = new Course("ENG101", "Calculus I", 3);
        Course engCourse2 = new Course("ENG102", "Physics I", 4);
        Course engCourse3 = new Course("ENG103", "Programming Basics", 3);

        engStudent.enrollCourse(engCourse1);
        engStudent.enrollCourse(engCourse2);
        engStudent.enrollCourse(engCourse3);

        try {
            engStudent.setCourseGrade("ENG101", 85);
            engStudent.setCourseGrade("ENG102", 90);
            engStudent.setCourseGrade("ENG103", 78);
        } catch (InvalidMarkException e) {
            System.out.println("Error setting grade for Engineering Student: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Enroll courses and set grades for Management Student
        Course mgmtCourse1 = new Course("MGT101", "Microeconomics", 3);
        Course mgmtCourse2 = new Course("MGT102", "Business Ethics", 3);
        Course mgmtCourse3 = new Course("MGT103", "Marketing Principles", 4);
        Course mgmtCourse4 = new Course("MGT104", "Financial Accounting", 4);

        mgmtStudent.enrollCourse(mgmtCourse1);
        mgmtStudent.enrollCourse(mgmtCourse2);
        mgmtStudent.enrollCourse(mgmtCourse3);
        mgmtStudent.enrollCourse(mgmtCourse4);

        try {
            mgmtStudent.setCourseGrade("MGT101", 70);
            mgmtStudent.setCourseGrade("MGT102", 80);
            mgmtStudent.setCourseGrade("MGT103", 95);
            mgmtStudent.setCourseGrade("MGT104", 88);
        } catch (InvalidMarkException e) {
            System.out.println("Error setting grade for Management Student: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Demonstrate runtime polymorphism and print reports
        System.out.println("--- Student Reports ---");
        System.out.println(engStudent.generateReport());
        System.out.println(mgmtStudent.generateReport());
        System.out.println();

        // Demonstrate transcripts
        System.out.println("--- Student Transcripts ---");
        System.out.println(engStudent.generateTranscript());
        System.out.println(mgmtStudent.generateTranscript());
        System.out.println();

        // Demonstrate access behavior of members
        System.out.println("--- Access Behavior Demonstration ---");

        // Public member access (getName() from Person)
        System.out.println("Public member (name) via getName() for Engineering Student: " + engStudent.getName());
        System.out.println("Public member (name) via getName() for Management Student: " + mgmtStudent.getName());

        // Protected member access (id from Person, accessed via a public method in Student)
        System.out.println("Protected member (id) via getProtectedId() for Engineering Student: " + engStudent.getProtectedId());
        System.out.println("Protected member (id) via getProtectedId() for Management Student: " + mgmtStudent.getProtectedId());

        // Private member access (name from Person) - requires reflection for demonstration
        // In a real-world scenario, direct access to private members is discouraged.
        try {
            Field privateNameField = Person.class.getDeclaredField("name");
            privateNameField.setAccessible(true); // Bypass private access restriction
            String privateName = (String) privateNameField.get(engStudent);
            System.out.println("Private member (name) via Reflection for Engineering Student: " + privateName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error accessing private member: " + e.getMessage());
        }

        try {
            Field privateNameField = Person.class.getDeclaredField("name");
            privateNameField.setAccessible(true);
            String privateName = (String) privateNameField.get(mgmtStudent);
            System.out.println("Private member (name) via Reflection for Management Student: " + privateName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error accessing private member: " + e.getMessage());
        }

        // Demonstrate exception handling for invalid marks
        System.out.println("\n--- Exception Handling Demonstration ---");
        try {
            engStudent.setCourseGrade("ENG101", 105); // Invalid mark
        } catch (InvalidMarkException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Caught unexpected exception: " + e.getMessage());
        }

        try {
            mgmtStudent.setCourseGrade("MGT999", 75); // Non-existent course
        } catch (InvalidMarkException e) {
            System.out.println("Caught unexpected exception: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
