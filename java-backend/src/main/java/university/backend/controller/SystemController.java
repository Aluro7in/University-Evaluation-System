package university.backend.controller;

import org.springframework.web.bind.annotation.*;
import university.backend.service.SemesterService;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {
    private final SemesterService semesters;
    public SystemController(SemesterService semesters){this.semesters=semesters;}

    @GetMapping("/current-semester")
    public Map<String,String> current(){return Map.of("semester",semesters.currentKey());}

    @PostMapping("/current-semester")
    public Map<String,String> set(@RequestParam String key){return Map.of("semester",semesters.setCurrent(key).key());}
}