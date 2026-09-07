package university.backend.policy;

import university.backend.domain.entity.EnrollmentEntity;
import university.backend.domain.entity.GradeEntity;
import university.backend.domain.enums.AcademicStanding;
import java.util.List;

public interface EvaluationPolicy {
    double calculateGpa(List<GradeEntity> grades);
    double calculateScore(List<GradeEntity> grades);
    String getPolicyName();
    String getDescription();
    AcademicStanding standing(double gpa);
    boolean shouldInclude(EnrollmentEntity enrollment);
}