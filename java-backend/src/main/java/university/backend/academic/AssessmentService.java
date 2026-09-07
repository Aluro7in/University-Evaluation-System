package university.backend.academic;

import java.util.List;

/** Deterministic assessment engine used by grade and reporting workflows. */
public final class AssessmentService {
    private AssessmentService() {}

    public static void validateWeights(List<AssessmentComponent> components) {
        if (components == null || components.isEmpty()) throw new IllegalArgumentException("At least one assessment is required");
        double total = components.stream().mapToDouble(AssessmentComponent::weight).sum();
        if (Math.abs(total - 100.0) > 0.0001) {
            throw new IllegalArgumentException("Assessment weights must total 100%; got " + total);
        }
    }

    public static double calculateFinalScore(List<AssessmentComponent> components) {
        validateWeights(components);
        return round(components.stream().mapToDouble(AssessmentComponent::weightedContribution).sum());
    }

    public static String classify(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    private static double round(double value) { return Math.round(value * 100.0) / 100.0; }
}
