package university.backend.planning;

import java.util.List;

public record CourseLoadPlan(
        int maxCredits,
        List<String> recommendedCourses,
        int projectedCredits
) {
    public CourseLoadPlan {
        recommendedCourses = recommendedCourses == null ? List.of() : List.copyOf(recommendedCourses);
        if (maxCredits < 0 || projectedCredits < 0) throw new IllegalArgumentException("Credit values cannot be negative");
    }

    public boolean withinLimit() { return projectedCredits <= maxCredits; }
}
