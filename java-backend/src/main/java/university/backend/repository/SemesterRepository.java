package university.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import university.backend.domain.entity.SemesterEntity;
import university.backend.domain.enums.SemesterType;
import java.util.*;
public interface SemesterRepository extends JpaRepository<SemesterEntity,Long>{
    Optional<SemesterEntity> findByAcademicYearAndTerm(Integer year,SemesterType term);
    Optional<SemesterEntity> findFirstByCurrentTrue();
}