package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.EvaluationService;
import university.backend.policy.GradeScale;
import java.util.Map;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {
    private final EvaluationService service;
    public EvaluationController(EvaluationService service){this.service=service;}

    @PostMapping("/gpa")
    public EvaluationResponse calculate(@Valid @RequestBody EvaluationRequest request){return service.calculate(request);}

    @GetMapping("/students/{studentId}")
    public EvaluationResponse student(@PathVariable Long studentId){return service.calculateForStudent(studentId);}

    @GetMapping("/grade-scale")
    public Map<String,Object> gradeScale(@RequestParam int percentage){
        GradeScale.validate(percentage);
        return Map.of(
                "percentage",percentage,
                "gpa",service.percentageToGpa(percentage),
                "letter",service.percentageToLetter(percentage),
                "passing",service.passes(percentage),
                "band",GradeScale.band(percentage)
        );
    }
}