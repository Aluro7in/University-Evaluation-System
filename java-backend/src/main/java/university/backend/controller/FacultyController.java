package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.FacultyService;
import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
    private final FacultyService service;
    public FacultyController(FacultyService service){this.service=service;}

    @GetMapping
    public List<FacultyResponse> list(@RequestParam(required=false) String department){return service.list(department);}

    @PostMapping
    public FacultyResponse create(@Valid @RequestBody CreateFacultyRequest request){return service.create(request);}
}