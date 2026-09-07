package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.EnrollmentService;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService service;
    public EnrollmentController(EnrollmentService service){this.service=service;}

    @GetMapping("/student/{studentId}")
    public List<EnrollmentResponse> byStudent(@PathVariable Long studentId){return service.byStudent(studentId);}

    @PostMapping
    public EnrollmentResponse enroll(@Valid @RequestBody EnrollmentRequest request){return service.enroll(request);}

    @PostMapping("/{id}/complete")
    public EnrollmentResponse complete(@PathVariable Long id){return service.complete(id);}

    @DeleteMapping("/{id}")
    public EnrollmentResponse drop(@PathVariable Long id){return service.drop(id);}
}