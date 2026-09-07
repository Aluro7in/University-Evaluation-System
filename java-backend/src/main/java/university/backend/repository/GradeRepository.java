package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.GradeEntity;
import java.util.*;
public interface GradeRepository extends JpaRepository<GradeEntity,Long>{
    Optional<GradeEntity> findByEnrollmentId(Long enrollmentId);
    List<GradeEntity> findByEnrollmentStudentId(Long studentId);
    List<GradeEntity> findByEnrollmentStudentIdAndFinalizedTrue(Long studentId);
}