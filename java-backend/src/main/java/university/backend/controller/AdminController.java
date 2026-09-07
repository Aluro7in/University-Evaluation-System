package university.backend.controller;

import org.springframework.web.bind.annotation.*;
import university.backend.service.SystemSummaryService;
import university.backend.service.AuditService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final SystemSummaryService summary;
    private final AuditService audit;

    public AdminController(SystemSummaryService summary,AuditService audit){
        this.summary=summary;this.audit=audit;
    }

    @GetMapping("/summary")
    public java.util.Map<String,Object> summary(){return summary.snapshot();}

    @GetMapping("/audit")
    public java.util.List<?> audit(){return audit.latest();}
}