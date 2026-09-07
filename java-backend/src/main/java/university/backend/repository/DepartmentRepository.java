package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.DepartmentEntity;
import java.util.*;
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long>{
    boolean existsByCode(String code);
    Optional<DepartmentEntity> findByCode(String code);
}