package university.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.AttendanceService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService service;
    public AttendanceController(AttendanceService service){this.service=service;}

    @PostMapping
    public AttendanceResponse mark(@Valid @RequestBody AttendanceRequest request){return service.mark(request);}

    @GetMapping("/enrollment/{enrollmentId}")
    public List<AttendanceResponse> list(@PathVariable Long enrollmentId){return service.byEnrollment(enrollmentId);}

    @GetMapping("/enrollment/{enrollmentId}/percentage")
    public Map<String,Object> percentage(@PathVariable Long enrollmentId){
        return Map.of("enrollmentId",enrollmentId,"attendancePercentage",service.percentage(enrollmentId));
    }
}