package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.GradeService;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService service;
    public GradeController(GradeService service){this.service=service;}

    @GetMapping("/student/{studentId}")
    public List<GradeResponse> byStudent(@PathVariable Long studentId){return service.byStudent(studentId);}

    @PostMapping
    public GradeResponse upsert(@Valid @RequestBody GradeRequest request){return service.upsert(request);}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){service.delete(id);}
}