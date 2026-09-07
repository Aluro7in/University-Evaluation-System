package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.AuditLogEntity;
import java.util.*;
import java.time.LocalDateTime;
public interface AuditLogRepository extends JpaRepository<AuditLogEntity,Long>{
    List<AuditLogEntity> findTop100ByOrderByCreatedAtDesc();
    List<AuditLogEntity> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime timestamp);
}