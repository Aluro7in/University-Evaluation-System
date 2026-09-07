package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.DepartmentService;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService service;
    public DepartmentController(DepartmentService service){this.service=service;}

    @GetMapping
    public List<DepartmentResponse> list(){return service.list();}

    @PostMapping
    public DepartmentResponse create(@Valid @RequestBody CreateDepartmentRequest request){return service.create(request);}
}