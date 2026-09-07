package university.backend.service;

import org.springframework.stereotype.Service;
import university.backend.domain.entity.CourseEntity;
import university.backend.domain.entity.EnrollmentEntity;
import university.backend.domain.entity.GradeEntity;
import university.backend.domain.entity.StudentEntity;
import university.backend.domain.enums.StudentType;
import university.backend.dto.EvaluationGrade;
import university.backend.dto.EvaluationRequest;
import university.backend.dto.EvaluationResponse;
import university.backend.policy.AcademicRules;
import university.backend.policy.EvaluationPolicy;
import university.backend.policy.EvaluationPolicyFactory;
import university.backend.policy.GradeScale;

import java.util.List;

/**
 * Application-level facade for all grade and GPA decisions.
 * The web application can send raw percentage scores here while
 * the policy layer decides which academic model applies.
 */
@Service
public class EvaluationService {
    private final EvaluationPolicyFactory factory;
    private final GradeService gradeService;
    private final StudentService studentService;

    public EvaluationService(EvaluationPolicyFactory factory,
                             GradeService gradeService,
                             StudentService studentService) {
        this.factory = factory;
        this.gradeService = gradeService;
        this.studentService = studentService;
    }

    public EvaluationResponse calculate(EvaluationRequest request) {
        StudentType type = StudentType.valueOf(request.studentType().trim().toUpperCase());
        EvaluationPolicy policy = factory.forType(type);

        List<GradeEntity> synthetic = request.grades().stream()
                .map(this::toSyntheticGrade)
                .toList();

        double gpa = policy.calculateGpa(synthetic);
        double average = policy.calculateScore(synthetic);
        int credits = request.grades().stream().mapToInt(EvaluationGrade::credits).sum();

        return new EvaluationResponse(
                gpa,
                average,
                policy.standing(gpa).name(),
                policy.getPolicyName(),
                request.grades().size(),
                credits
        );
    }

    public EvaluationResponse calculateForStudent(Long studentId) {
        StudentEntity student = studentService.entity(studentId);
        EvaluationPolicy policy = factory.forType(student.getType());

        List<GradeEntity> grades = gradeService.entitiesByStudent(studentId);
        double gpa = policy.calculateGpa(grades);
        double average = policy.calculateScore(grades);
        int credits = AcademicRules.totalCredits(grades);

        return new EvaluationResponse(
                gpa,
                average,
                policy.standing(gpa).name(),
                policy.getPolicyName(),
                grades.size(),
                credits
        );
    }

    public double percentageToGpa(int percentage) {
        return GradeScale.toGpa(percentage);
    }

    public String percentageToLetter(int percentage) {
        return GradeScale.toLetter(percentage).name();
    }

    public boolean passes(int percentage) {
        GradeScale.validate(percentage);
        return AcademicRules.passes(percentage);
    }

    private GradeEntity toSyntheticGrade(EvaluationGrade input) {
        CourseEntity course = new CourseEntity(
                input.courseCode() == null ? "EVAL" : input.courseCode(),
                input.courseName() == null ? "Evaluation Input" : input.courseName(),
                input.credits(),
                null,
                null,
                500
        );

        StudentEntity student = new StudentEntity(
                "EVAL",
                "Evaluation Request",
                StudentType.ENGINEERING,
                null,
                2026
        );

        EnrollmentEntity enrollment = new EnrollmentEntity(student, course, "EVALUATION");
        return new GradeEntity(
                enrollment,
                input.percentage(),
                GradeScale.toGpa(input.percentage()),
                GradeScale.toLetter(input.percentage()),
                null
        );
    }
}