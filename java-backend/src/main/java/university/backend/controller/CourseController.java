package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.CourseService;
import university.backend.service.PrerequisiteService;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService service;
    private final PrerequisiteService prerequisiteService;

    public CourseController(CourseService service,PrerequisiteService prerequisiteService){
        this.service=service;this.prerequisiteService=prerequisiteService;
    }

    @GetMapping
    public List<CourseResponse> list(@RequestParam(required=false) String department){
        return service.list(department);
    }

    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable Long id){return CourseResponse.from(service.entity(id));}

    @PostMapping
    public CourseResponse create(@Valid @RequestBody CreateCourseRequest request){return service.create(request);}

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id,@Valid @RequestBody CreateCourseRequest request){
        return service.update(id,request);
    }

    @GetMapping("/{courseId}/missing-prerequisites/{studentId}")
    public List<String> missing(@PathVariable Long courseId,@PathVariable Long studentId){
        return prerequisiteService.missing(studentId,service.entity(courseId));
    }
}