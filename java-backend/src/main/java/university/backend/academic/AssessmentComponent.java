package university.backend.academic;

import java.util.Objects;

/** A weighted component of a course assessment. */
public record AssessmentComponent(
        String name,
        double weight,
        int score
) {
    public AssessmentComponent {
        Objects.requireNonNull(name, "name");
        if (name.isBlank()) throw new IllegalArgumentException("Assessment name is required");
        if (weight <= 0 || weight > 100) throw new IllegalArgumentException("Weight must be in (0, 100]");
        if (score < 0 || score > 100) throw new IllegalArgumentException("Score must be between 0 and 100");
    }

    public double weightedContribution() {
        return score * weight / 100.0;
    }
}
