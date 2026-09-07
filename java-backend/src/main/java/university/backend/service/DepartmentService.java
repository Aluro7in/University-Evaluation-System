package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.DepartmentEntity;
import university.backend.dto.CreateDepartmentRequest;
import university.backend.dto.DepartmentResponse;
import university.backend.exception.DuplicateResourceException;
import university.backend.exception.ResourceNotFoundException;
import university.backend.repository.DepartmentRepository;

import java.util.List;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository repository;
    public DepartmentService(DepartmentRepository repository){this.repository=repository;}

    @Transactional(readOnly=true)
    public List<DepartmentResponse> list(){return repository.findAll().stream().map(DepartmentResponse::from).toList();}

    public DepartmentResponse create(CreateDepartmentRequest request){
        String code=request.code().trim().toUpperCase();
        if(repository.existsByCode(code))throw new DuplicateResourceException("Department already exists: "+code);
        return DepartmentResponse.from(repository.save(
                new DepartmentEntity(code,request.name().trim(),request.school())));
    }

    @Transactional(readOnly=true)
    public DepartmentEntity entity(String code){
        return repository.findByCode(code.trim().toUpperCase())
                .orElseThrow(()->new ResourceNotFoundException("Department "+code+" not found"));
    }

    public long count(){return repository.count();}
}