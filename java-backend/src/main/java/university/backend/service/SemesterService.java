package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.SemesterEntity;
import university.backend.domain.enums.SemesterType;
import university.backend.repository.SemesterRepository;
import java.time.LocalDate;

@Service
@Transactional
public class SemesterService {
    private final SemesterRepository repository;
    public SemesterService(SemesterRepository repository){this.repository=repository;}

    public SemesterEntity resolve(String key){
        if(key==null||!key.matches("\\d{4}-(SPRING|SUMMER|FALL|WINTER)"))
            throw new IllegalArgumentException("Semester must match YYYY-SPRING, YYYY-SUMMER, YYYY-FALL or YYYY-WINTER");
        String[] p=key.split("-",2);
        int year=Integer.parseInt(p[0]);
        SemesterType term=SemesterType.valueOf(p[1]);
        return repository.findByAcademicYearAndTerm(year,term).orElseGet(
                ()->repository.save(new SemesterEntity(year,term,
                        LocalDate.of(year,1,1),LocalDate.of(year,12,31),false)));
    }

    public SemesterEntity setCurrent(String key){
        SemesterEntity target=resolve(key);
        repository.findFirstByCurrentTrue().ifPresent(old->{old.setCurrent(false);repository.save(old);});
        target.setCurrent(true);return repository.save(target);
    }

    @Transactional(readOnly=true)
    public String currentKey(){return repository.findFirstByCurrentTrue().map(SemesterEntity::key).orElse("CURRENT-UNSET");}
}