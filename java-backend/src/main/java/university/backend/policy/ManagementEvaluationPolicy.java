package university.backend.policy;

import university.backend.domain.entity.EnrollmentEntity;
import university.backend.domain.entity.GradeEntity;
import university.backend.domain.enums.AcademicStanding;
import university.backend.domain.enums.EnrollmentStatus;
import java.util.List;

public class ManagementEvaluationPolicy implements EvaluationPolicy {
    @Override
    public double calculateGpa(List<GradeEntity> grades) {
        double weightedPoints = 0.0;
int credits = 0;
for (GradeEntity grade : grades) {
    if (grade == null || grade.getEnrollment() == null || !shouldInclude(grade.getEnrollment())) continue;
    int courseCredits = grade.getEnrollment().getCourse().getCredits();
    weightedPoints += grade.getGpa() * courseCredits;
    credits += courseCredits;
}
return credits == 0 ? 0.0 : GradeScale.round2(weightedPoints / credits);

    }

    @Override
    public double calculateScore(List<GradeEntity> grades) {
        if (grades == null || grades.isEmpty()) return 0.0;
        double total = 0.0;
        int count = 0;
        for (GradeEntity grade : grades) {
            if (grade != null && grade.getEnrollment() != null && shouldInclude(grade.getEnrollment())) {
                total += grade.getPercentage();
                count++;
            }
        }
        return count == 0 ? 0.0 : GradeScale.round2(total / count);
    }

    @Override
    public String getPolicyName() {
        return "Management Credit Weighted Average";
    }

    @Override
    public String getDescription() {
        return "Management weights each 4.0-scale grade by course credits.";
    }

    @Override
    public AcademicStanding standing(double gpa) {
        if (gpa >= 3.5) return AcademicStanding.EXCELLENT;
        if (gpa >= 3.0) return AcademicStanding.GOOD;
        if (gpa >= 2.0) return AcademicStanding.SATISFACTORY;
        if (gpa >= 1.0) return AcademicStanding.PROBATION;
        return AcademicStanding.AT_RISK;
    }

    @Override
    public boolean shouldInclude(EnrollmentEntity enrollment) {
        if (enrollment == null) return false;
        EnrollmentStatus status = enrollment.getStatus();
        return status == EnrollmentStatus.ACTIVE || status == EnrollmentStatus.COMPLETED;
    }
}