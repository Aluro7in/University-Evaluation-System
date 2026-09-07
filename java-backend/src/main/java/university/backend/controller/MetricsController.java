package university.backend.controller;

import org.springframework.web.bind.annotation.*;
import university.backend.analysis.GpaDistributionAnalyzer;
import university.backend.service.SystemSummaryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    private final SystemSummaryService summary;
    public MetricsController(SystemSummaryService summary){this.summary=summary;}

    @GetMapping("/summary")
    public Map<String,Object> summary(){return summary.snapshot();}

    @PostMapping("/gpa-distribution")
    public Map<String,Long> gpaDistribution(@RequestBody List<Double> values){
        return GpaDistributionAnalyzer.distribution(values);
    }
}