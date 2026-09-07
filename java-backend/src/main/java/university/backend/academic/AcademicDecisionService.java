package university.backend.academic;

public class AcademicDecisionService {
    public AcademicDecision evaluateEnrollment(double gpa, double attendanceRate, int failedPrerequisites) {
        if (failedPrerequisites > 0) return new AcademicDecision(false, "BLOCKED", "Prerequisite requirements are incomplete");
        if (attendanceRate < 65) return new AcademicDecision(false, "BLOCKED", "Attendance is below the minimum threshold");
        if (gpa < 1.5) return new AcademicDecision(false, "ADVISOR_REVIEW", "GPA requires academic advising");
        if (gpa < 2.0) return new AcademicDecision(true, "LIMITED", "Enrollment permitted with reduced course load");
        return new AcademicDecision(true, "APPROVED", "Student satisfies enrollment rules");
    }

    public AcademicDecision evaluateGraduation(int earnedCredits, int requiredCredits, double gpa) {
        if (earnedCredits < requiredCredits) return new AcademicDecision(false, "INCOMPLETE", "Required credits are not complete");
        if (gpa < 2.0) return new AcademicDecision(false, "INCOMPLETE", "Minimum graduation GPA has not been reached");
        return new AcademicDecision(true, "READY", "Student satisfies graduation rules");
    }
}
