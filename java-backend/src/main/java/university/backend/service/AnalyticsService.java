package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.dto.AnalyticsResponse;
import university.backend.dto.GradeResponse;
import university.backend.domain.entity.StudentEntity;
import university.backend.repository.EnrollmentRepository;
import university.backend.repository.GradeRepository;
import university.backend.policy.AcademicRules;

import java.util.List;

@Service
public class AnalyticsService {
    private final StudentService students;
    private final CourseService courses;
    private final EnrollmentRepository enrollments;
    private final GradeRepository grades;

    public AnalyticsService(StudentService students,CourseService courses,
                            EnrollmentRepository enrollments,GradeRepository grades){
        this.students=students;this.courses=courses;this.enrollments=enrollments;this.grades=grades;
    }

    @Transactional(readOnly=true)
    public AnalyticsResponse overview(){
        List<StudentEntity> all=students.allEntities();
        List<GradeResponse> rows=all.stream()
                .flatMap(s->grades.findByEnrollmentStudentId(s.getId()).stream().map(GradeResponse::from))
                .toList();

        double avgGpa=rows.stream().mapToDouble(GradeResponse::gpa).average().orElse(0);
        double avgPct=rows.stream().mapToInt(GradeResponse::percentage).average().orElse(0);
        long risk=0,probation=0,dean=0;

        for(StudentEntity student:all){
            if(!Boolean.TRUE.equals(student.getActive()))continue;
            var g=grades.findByEnrollmentStudentId(student.getId());
            double gpa=g.stream().mapToDouble(GradeResponse::from).mapToDouble(GradeResponse::gpa).average().orElse(0);
            int credits=AcademicRules.totalCredits(g);
            if(gpa<1.0)risk++;else if(gpa<2.0)probation++;
            if(AcademicRules.deanListEligible(gpa,credits))dean++;
        }

        return new AnalyticsResponse(
                students.count(),students.activeCount(),courses.count(),enrollments.count(),
                grades.count(),round(avgGpa),round(avgPct),risk,probation,dean);
    }

    private double round(double v){return Math.round(v*100.0)/100.0;}
}