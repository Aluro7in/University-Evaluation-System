package university.backend.controller;

import org.springframework.web.bind.annotation.*;
import university.backend.dto.*;
import university.backend.service.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analytics;
    private final RankingService ranking;
    private final RiskAnalyzerService riskAnalyzer;

    public AnalyticsController(AnalyticsService analytics,RankingService ranking,RiskAnalyzerService riskAnalyzer){
        this.analytics=analytics;this.ranking=ranking;this.riskAnalyzer=riskAnalyzer;
    }

    @GetMapping("/overview")
    public AnalyticsResponse overview(){return analytics.overview();}

    @GetMapping("/ranking")
    public List<RankingEntry> ranking(@RequestParam(defaultValue="10") int limit){return ranking.top(limit);}

    @GetMapping("/risk")
    public Map<String,String> risk(@RequestParam double gpa,@RequestParam double attendance,
                                   @RequestParam(defaultValue="0") int failedCourses,
                                   @RequestParam(defaultValue="false") boolean incomplete){
        var result=riskAnalyzer.classify("anonymous",gpa,attendance,failedCourses,incomplete);
        return Map.of("risk",result.riskLevel(),"recommendation",result.recommendation());
    }
}