package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.CourseEntity;
import university.backend.dto.*;
import university.backend.exception.DuplicateResourceException;
import university.backend.exception.ResourceNotFoundException;
import university.backend.repository.CourseRepository;

import java.util.List;

@Service
@Transactional
public class CourseService {
    private final CourseRepository repository;
    public CourseService(CourseRepository repository){this.repository=repository;}

    @Transactional(readOnly=true)
    public List<CourseResponse> list(String department){
        List<CourseEntity> data=(department==null||department.isBlank())
                ?repository.findAllByActiveTrueOrderByCourseCodeAsc()
                :repository.findByDepartmentIgnoreCaseOrderByCourseCodeAsc(department.trim());
        return data.stream().map(CourseResponse::from).toList();
    }

    @Transactional(readOnly=true)
    public CourseEntity entity(Long id){
        return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course "+id+" not found"));
    }

    @Transactional(readOnly=true)
    public CourseEntity byCode(String code){
        return repository.findByCourseCode(code.trim().toUpperCase())
                .orElseThrow(()->new ResourceNotFoundException("Course "+code+" not found"));
    }

    public CourseResponse create(CreateCourseRequest request){
        String code=request.courseCode().trim().toUpperCase();
        if(repository.existsByCourseCode(code))throw new DuplicateResourceException("Course code already exists: "+code);
        CourseEntity c=new CourseEntity(code,request.courseName().trim(),request.credits(),
                normalize(request.description()),normalize(request.department()),request.capacity());
        c.setPrerequisiteCodes(request.prerequisiteCodes());
        return CourseResponse.from(repository.save(c));
    }

    public CourseResponse update(Long id,CreateCourseRequest request){
        CourseEntity c=entity(id);
        c.setCourseName(request.courseName().trim());c.setCredits(request.credits());
        c.setDescription(normalize(request.description()));c.setDepartment(normalize(request.department()));
        c.setCapacity(request.capacity());c.setPrerequisiteCodes(request.prerequisiteCodes());
        return CourseResponse.from(repository.save(c));
    }

    public void deactivate(Long id){CourseEntity c=entity(id);c.setActive(false);repository.save(c);}
    public long count(){return repository.count();}
    public List<CourseEntity> allEntities(){return repository.findAll();}
    private String normalize(String value){if(value==null)return null;String n=value.trim();return n.isEmpty()?null:n;}
}