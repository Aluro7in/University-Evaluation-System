package university.backend.business;

import java.util.ArrayList;
import java.util.List;

/** Produces an auditable graduation readiness decision. */
public class GraduationAuditService {
    public record Candidate(int earnedCredits, int requiredCredits, double gpa,
                            int completedCoreCourses, int requiredCoreCourses,
                            double attendanceRate) {}
    public record Audit(boolean ready, List<String> blockers, List<String> checks) {
        public Audit { blockers = List.copyOf(blockers); checks = List.copyOf(checks); }
    }

    public Audit audit(Candidate candidate) {
        List<String> blockers = new ArrayList<>();
        List<String> checks = new ArrayList<>();
        if (candidate.earnedCredits() >= candidate.requiredCredits()) checks.add("CREDITS_COMPLETE");
        else blockers.add("CREDITS_INCOMPLETE");
        if (candidate.gpa() >= 2.0) checks.add("GPA_MET");
        else blockers.add("GPA_BELOW_2_0");
        if (candidate.completedCoreCourses() >= candidate.requiredCoreCourses()) checks.add("CORE_COMPLETE");
        else blockers.add("CORE_COURSES_INCOMPLETE");
        if (candidate.attendanceRate() >= 75) checks.add("ATTENDANCE_ACCEPTABLE");
        else blockers.add("ATTENDANCE_REVIEW");
        return new Audit(blockers.isEmpty(), blockers, checks);
    }
}
