package university.backend.policy;

import org.springframework.stereotype.Component;
import university.backend.domain.enums.StudentType;

@Component
public class EvaluationPolicyFactory {
    private final EvaluationPolicy engineering = new EngineeringEvaluationPolicy();
    private final EvaluationPolicy management = new ManagementEvaluationPolicy();
    private final EvaluationPolicy graduate = new GraduateEvaluationPolicy();

    public EvaluationPolicy forType(StudentType type) {
        if (type == null) throw new IllegalArgumentException("Student type is required");
        return switch (type) {
            case ENGINEERING -> engineering;
            case MANAGEMENT -> management;
            case GRADUATE -> graduate;
        };
    }
}