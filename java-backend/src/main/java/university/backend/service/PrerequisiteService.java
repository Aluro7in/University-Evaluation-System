package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.CourseEntity;
import university.backend.policy.AcademicRules;
import university.backend.repository.GradeRepository;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class PrerequisiteService {
    private final GradeRepository grades;
    public PrerequisiteService(GradeRepository grades){this.grades=grades;}

    public List<String> completedCodes(Long studentId){
        return grades.findByEnrollmentStudentIdAndFinalizedTrue(studentId).stream()
                .filter(g->AcademicRules.passes(g.getPercentage()))
                .map(g->g.getEnrollment().getCourse().getCourseCode()).toList();
    }

    public boolean areSatisfied(Long studentId,CourseEntity course){
        return AcademicRules.prerequisitesSatisfied(course,completedCodes(studentId));
    }

    public List<String> missing(Long studentId,CourseEntity course){
        var completed=completedCodes(studentId).stream().map(String::toUpperCase).toList();
        return course.getPrerequisiteCodes().stream()
                .filter(p->!completed.contains(p.toUpperCase())).toList();
    }
}