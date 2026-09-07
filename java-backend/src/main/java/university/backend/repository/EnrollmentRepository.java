package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.EnrollmentEntity;
import university.backend.domain.enums.EnrollmentStatus;
import java.util.*;
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity,Long>{
    List<EnrollmentEntity> findByStudentIdOrderByEnrolledAtDesc(Long studentId);
    List<EnrollmentEntity> findByCourseIdOrderByEnrolledAtDesc(Long courseId);
    List<EnrollmentEntity> findByStudentIdAndStatus(Long studentId,EnrollmentStatus status);
    boolean existsByStudentIdAndCourseIdAndStatus(Long studentId,Long courseId,EnrollmentStatus status);
    long countByCourseIdAndStatus(Long courseId,EnrollmentStatus status);
}