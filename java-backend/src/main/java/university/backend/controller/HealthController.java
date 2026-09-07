package university.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;

@RestController
public class HealthController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/health")
    public Map<String,Object> health(){
        return Map.of("status","UP","service",serviceName,"timestamp",Instant.now().toString());
    }

    @GetMapping("/api/health")
    public Map<String,Object> apiHealth(){
        return Map.of("status","UP","service",serviceName,"apiVersion","2.0");
    }
}