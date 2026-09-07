package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.StudentEntity;
import university.backend.domain.enums.StudentType;
import java.util.*;
public interface StudentRepository extends JpaRepository<StudentEntity,Long>{
    boolean existsByStudentId(String studentId);
    Optional<StudentEntity> findByStudentId(String studentId);
    List<StudentEntity> findAllByActiveTrueOrderByNameAsc();
    List<StudentEntity> findAllByTypeOrderByNameAsc(StudentType type);
}