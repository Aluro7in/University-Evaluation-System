package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.dto.*;
import university.backend.domain.entity.StudentEntity;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

/**
 * Builds official-style transcript responses from persisted records.
 * It deliberately delegates calculations to EvaluationService so
 * the transcript cannot accidentally use a second GPA algorithm.
 */
@Service
public class TranscriptService {
    private final StudentService students;
    private final GradeService grades;
    private final EvaluationService evaluation;

    public TranscriptService(StudentService students,GradeService grades,EvaluationService evaluation){
        this.students=students;this.grades=grades;this.evaluation=evaluation;
    }

    @Transactional(readOnly=true)
    public TranscriptResponse build(Long studentId){
        StudentEntity student=students.entity(studentId);

        List<GradeResponse> rows=grades.byStudent(studentId).stream()
                .sorted(Comparator.comparing(GradeResponse::courseCode))
                .toList();

        EvaluationResponse summary=evaluation.calculateForStudent(studentId);

        return new TranscriptResponse(
                StudentResponse.from(student),
                rows,
                summary.gpa(),
                summary.averagePercentage(),
                summary.totalCredits(),
                summary.calculationPolicy(),
                summary.academicStanding(),
                Instant.now().toString()
        );
    }

    @Transactional(readOnly=true)
    public String summary(Long studentId){
        TranscriptResponse t=build(studentId);
        return "Transcript "+t.student().studentId()+" | GPA "+String.format("%.2f",t.gpa())
                +" | Credits "+t.totalCredits()+" | Standing "+t.academicStanding();
    }
}