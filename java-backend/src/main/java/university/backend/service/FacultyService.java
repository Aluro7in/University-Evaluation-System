package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.FacultyEntity;
import university.backend.dto.CreateFacultyRequest;
import university.backend.dto.FacultyResponse;
import university.backend.exception.DuplicateResourceException;
import university.backend.repository.FacultyRepository;

import java.util.List;

@Service
@Transactional
public class FacultyService {
    private final FacultyRepository repository;
    public FacultyService(FacultyRepository repository){this.repository=repository;}

    @Transactional(readOnly=true)
    public List<FacultyResponse> list(String department){
        List<FacultyEntity> data=(department==null||department.isBlank())
                ?repository.findAll()
                :repository.findByDepartmentCodeOrderByNameAsc(department.trim().toUpperCase());
        return data.stream().map(FacultyResponse::from).toList();
    }

    public FacultyResponse create(CreateFacultyRequest request){
        String email=request.email().trim().toLowerCase();
        if(repository.findByEmailIgnoreCase(email).isPresent())
            throw new DuplicateResourceException("Faculty email already exists");
        String dept=request.departmentCode()==null?null:request.departmentCode().trim().toUpperCase();
        return FacultyResponse.from(repository.save(new FacultyEntity(
                request.name().trim(),email,dept,request.specialization())));
    }
}