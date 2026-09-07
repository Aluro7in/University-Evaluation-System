package university.backend.business;

import java.util.ArrayList;
import java.util.List;

/** Transparent scholarship decision rules based on academic evidence. */
public class ScholarshipEligibilityService {
    public record Candidate(double gpa, double attendance, int earnedCredits, int disciplinaryPoints) {}
    public record Decision(boolean eligible, String tier, int score, List<String> reasons) {
        public Decision { reasons = reasons == null ? List.of() : List.copyOf(reasons); }
    }

    public Decision evaluate(Candidate candidate) {
        if (candidate == null) throw new IllegalArgumentException("Candidate is required");
        int score = 0;
        List<String> reasons = new ArrayList<>();
        if (candidate.gpa() >= 3.7) { score += 50; reasons.add("GPA >= 3.7"); }
        else if (candidate.gpa() >= 3.3) { score += 40; reasons.add("GPA >= 3.3"); }
        else if (candidate.gpa() >= 3.0) { score += 30; reasons.add("GPA >= 3.0"); }
        if (candidate.attendance() >= 90) { score += 25; reasons.add("Attendance >= 90%"); }
        else if (candidate.attendance() >= 80) { score += 15; reasons.add("Attendance >= 80%"); }
        if (candidate.earnedCredits() >= 60) { score += 15; reasons.add("60+ credits earned"); }
        else if (candidate.earnedCredits() >= 30) { score += 10; reasons.add("30+ credits earned"); }
        score -= Math.min(20, Math.max(0, candidate.disciplinaryPoints() * 5));
        if (candidate.disciplinaryPoints() > 0) reasons.add("Disciplinary points reduce score");
        String tier = score >= 80 ? "FULL" : score >= 60 ? "PARTIAL" : score >= 45 ? "RESERVE" : "NONE";
        return new Decision(!tier.equals("NONE"), tier, Math.max(0, score), reasons);
    }
}
