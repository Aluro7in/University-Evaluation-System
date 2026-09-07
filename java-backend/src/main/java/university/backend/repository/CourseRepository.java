package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.CourseEntity;
import java.util.*;
public interface CourseRepository extends JpaRepository<CourseEntity,Long>{
    boolean existsByCourseCode(String code);
    Optional<CourseEntity> findByCourseCode(String code);
    List<CourseEntity> findAllByActiveTrueOrderByCourseCodeAsc();
    List<CourseEntity> findByDepartmentIgnoreCaseOrderByCourseCodeAsc(String department);
}