package university.backend.planning;

import java.util.ArrayList;
import java.util.List;

/** Rule-based planner for recommending a safe semester course load. */
public class AcademicPlanningService {
    public CourseLoadPlan plan(double currentGpa, int earnedCredits, int targetCredits, List<String> eligibleCourses) {
        if (earnedCredits < 0 || targetCredits < 0) throw new IllegalArgumentException("Credits cannot be negative");
        int maxCredits = calculateMaximumCredits(currentGpa, earnedCredits, targetCredits);
        List<String> recommendations = new ArrayList<>();
        int projected = 0;
        for (String course : eligibleCourses == null ? List.<String>of() : eligibleCourses) {
            if (projected + 3 > maxCredits) break;
            recommendations.add(course);
            projected += 3;
        }
        return new CourseLoadPlan(maxCredits, recommendations, projected);
    }

    public int calculateMaximumCredits(double gpa, int earnedCredits, int targetCredits) {
        int base = earnedCredits < 30 ? 15 : 18;
        if (gpa >= 3.5) base += 3;
        if (gpa < 2.0) base -= 3;
        return Math.max(6, Math.min(base, targetCredits > 0 ? targetCredits : base));
    }
}
