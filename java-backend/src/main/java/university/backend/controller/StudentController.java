package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service){this.service=service;}

    @GetMapping
    public List<StudentResponse> list(@RequestParam(required=false) String type){return service.list(type);}

    @GetMapping("/{id}")
    public StudentResponse get(@PathVariable Long id){return service.get(id);}

    @PostMapping
    public StudentResponse create(@Valid @RequestBody CreateStudentRequest request){return service.create(request);}

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id,@Valid @RequestBody UpdateStudentRequest request){
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id){service.deactivate(id);}
}