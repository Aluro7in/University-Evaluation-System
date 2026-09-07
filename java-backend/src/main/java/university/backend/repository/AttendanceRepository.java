package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.AttendanceEntity;
import java.time.LocalDate;
import java.util.*;
public interface AttendanceRepository extends JpaRepository<AttendanceEntity,Long>{
    List<AttendanceEntity> findByEnrollmentIdOrderByAttendanceDateDesc(Long enrollmentId);
    long countByEnrollmentId(Long enrollmentId);
    long countByEnrollmentIdAndStatus(Long enrollmentId,String status);
    Optional<AttendanceEntity> findByEnrollmentIdAndAttendanceDate(Long enrollmentId,LocalDate date);
}