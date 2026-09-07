package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.*;
import university.backend.domain.enums.*;
import university.backend.dto.*;
import university.backend.exception.InvalidGradeException;
import university.backend.exception.ResourceNotFoundException;
import university.backend.policy.GradeScale;
import university.backend.repository.GradeRepository;

import java.util.List;

@Service
@Transactional
public class GradeService {
    private final GradeRepository repository;
    private final EnrollmentService enrollments;
    private final AuditService audit;

    public GradeService(GradeRepository repository,EnrollmentService enrollments,AuditService audit){
        this.repository=repository;this.enrollments=enrollments;this.audit=audit;
    }

    @Transactional(readOnly=true)
    public List<GradeResponse> byStudent(Long studentId){
        return repository.findByEnrollmentStudentId(studentId).stream().map(GradeResponse::from).toList();
    }

    @Transactional(readOnly=true)
    public List<GradeEntity> entitiesByStudent(Long studentId){
        return repository.findByEnrollmentStudentId(studentId);
    }

    @Transactional(readOnly=true)
    public GradeEntity entity(Long id){
        return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Grade "+id+" not found"));
    }

    public GradeResponse upsert(GradeRequest request){
        if(request.percentage()==null)throw new InvalidGradeException("Percentage is required");
        GradeScale.validate(request.percentage());
        EnrollmentEntity enrollment=enrollments.entity(request.enrollmentId());

        GradeEntity grade=repository.findByEnrollmentId(enrollment.getId()).orElseGet(
                ()->new GradeEntity(enrollment,request.percentage(),GradeScale.toGpa(request.percentage()),
                        GradeScale.toLetter(request.percentage()),request.instructorNote()));

        grade.setPercentage(request.percentage());
        grade.setGpa(GradeScale.toGpa(request.percentage()));
        grade.setLetter(GradeScale.toLetter(request.percentage()));
        grade.setInstructorNote(request.instructorNote());

        if(Boolean.TRUE.equals(request.finalize())){
            grade.setFinalized(true);enrollment.setStatus(EnrollmentStatus.COMPLETED);
        }

        GradeEntity saved=repository.save(grade);
        audit.write(AuditAction.GRADE_POST,"system","Grade",String.valueOf(saved.getId()),
                enrollment.getStudent().getStudentId()+" / "+enrollment.getCourse().getCourseCode()+" = "+request.percentage());
        return GradeResponse.from(saved);
    }

    public void delete(Long id){entity(id);repository.deleteById(id);audit.write(AuditAction.DELETE,"system","Grade",String.valueOf(id),"Grade deleted");}
    public long count(){return repository.count();}
}