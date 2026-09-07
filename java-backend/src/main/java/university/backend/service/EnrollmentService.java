package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.*;
import university.backend.domain.enums.*;
import university.backend.dto.*;
import university.backend.exception.BusinessRuleException;
import university.backend.exception.ResourceNotFoundException;
import university.backend.repository.EnrollmentRepository;

import java.util.List;

@Service
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository repository;
    private final StudentService students;
    private final CourseService courses;
    private final SemesterService semesters;
    private final PrerequisiteService prerequisites;
    private final AuditService audit;

    public EnrollmentService(EnrollmentRepository repository,StudentService students,CourseService courses,
                             SemesterService semesters,PrerequisiteService prerequisites,AuditService audit){
        this.repository=repository;this.students=students;this.courses=courses;this.semesters=semesters;
        this.prerequisites=prerequisites;this.audit=audit;
    }

    @Transactional(readOnly=true)
    public List<EnrollmentResponse> byStudent(Long studentId){
        return repository.findByStudentIdOrderByEnrolledAtDesc(studentId).stream()
                .map(EnrollmentResponse::from).toList();
    }

    @Transactional(readOnly=true)
    public EnrollmentEntity entity(Long id){
        return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Enrollment "+id+" not found"));
    }

    public EnrollmentResponse enroll(EnrollmentRequest request){
        StudentEntity student=students.entity(request.studentId());
        CourseEntity course=courses.entity(request.courseId());
        semesters.resolve(request.semesterKey());

        if(!Boolean.TRUE.equals(student.getActive()))throw new BusinessRuleException("Student is inactive");
        if(!Boolean.TRUE.equals(course.getActive()))throw new BusinessRuleException("Course is inactive");
        if(!course.hasCapacity())throw new BusinessRuleException("Course is full");
        if(repository.existsByStudentIdAndCourseIdAndStatus(student.getId(),course.getId(),EnrollmentStatus.ACTIVE))
            throw new BusinessRuleException("Student is already enrolled in "+course.getCourseCode());
        if(!prerequisites.areSatisfied(student.getId(),course))
            throw new BusinessRuleException("Course prerequisites are not satisfied");

        EnrollmentEntity saved=repository.save(new EnrollmentEntity(student,course,request.semesterKey().trim().toUpperCase()));
        audit.write(AuditAction.ENROLL,"system","Enrollment",String.valueOf(saved.getId()),
                student.getStudentId()+" -> "+course.getCourseCode());
        return EnrollmentResponse.from(saved);
    }

    public EnrollmentResponse drop(Long id){
        EnrollmentEntity e=entity(id);e.setStatus(EnrollmentStatus.DROPPED);repository.save(e);
        audit.write(AuditAction.UNENROLL,"system","Enrollment",String.valueOf(id),"Enrollment dropped");
        return EnrollmentResponse.from(e);
    }

    public EnrollmentResponse complete(Long id){
        EnrollmentEntity e=entity(id);e.setStatus(EnrollmentStatus.COMPLETED);repository.save(e);
        return EnrollmentResponse.from(e);
    }

    public long count(){return repository.count();}
}