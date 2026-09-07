package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.FacultyEntity;
import java.util.*;
public interface FacultyRepository extends JpaRepository<FacultyEntity,Long>{
    Optional<FacultyEntity> findByEmailIgnoreCase(String email);
    List<FacultyEntity> findByDepartmentCodeOrderByNameAsc(String departmentCode);
}