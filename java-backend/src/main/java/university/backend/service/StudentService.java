package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.StudentEntity;
import university.backend.domain.enums.StudentType;
import university.backend.dto.*;
import university.backend.exception.DuplicateResourceException;
import university.backend.exception.ResourceNotFoundException;
import university.backend.repository.StudentRepository;

import java.util.List;

@Service
@Transactional
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository){this.repository=repository;}

    @Transactional(readOnly=true)
    public List<StudentResponse> list(String type){
        if(type==null||type.isBlank()){
            return repository.findAllByActiveTrueOrderByNameAsc().stream().map(StudentResponse::from).toList();
        }
        StudentType value=StudentType.valueOf(type.trim().toUpperCase());
        return repository.findAllByTypeOrderByNameAsc(value).stream().map(StudentResponse::from).toList();
    }

    @Transactional(readOnly=true)
    public StudentEntity entity(Long id){
        return repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Student "+id+" not found"));
    }

    @Transactional(readOnly=true)
    public StudentEntity byStudentId(String studentId){
        return repository.findByStudentId(studentId.trim().toUpperCase())
                .orElseThrow(()->new ResourceNotFoundException("Student "+studentId+" not found"));
    }

    @Transactional(readOnly=true)
    public StudentResponse get(Long id){return StudentResponse.from(entity(id));}

    public StudentResponse create(CreateStudentRequest request){
        String id=request.studentId().trim().toUpperCase();
        if(repository.existsByStudentId(id))throw new DuplicateResourceException("Student ID already exists: "+id);
        return StudentResponse.from(repository.save(new StudentEntity(id,request.name().trim(),request.type(),
                normalize(request.major()),request.enrollmentYear())));
    }

    public StudentResponse update(Long id,UpdateStudentRequest request){
        StudentEntity s=entity(id);
        s.setName(request.name().trim()); s.setType(request.type()); s.setMajor(normalize(request.major()));
        s.setEnrollmentYear(request.enrollmentYear()); s.setActive(request.active());
        return StudentResponse.from(repository.save(s));
    }

    public void deactivate(Long id){
        StudentEntity s=entity(id);s.setActive(false);repository.save(s);
    }

    public long count(){return repository.count();}
    public long activeCount(){return repository.findAllByActiveTrueOrderByNameAsc().size();}
    public List<StudentEntity> allEntities(){return repository.findAll();}

    private String normalize(String value){
        if(value==null)return null;String n=value.trim();return n.isEmpty()?null:n;
    }
}