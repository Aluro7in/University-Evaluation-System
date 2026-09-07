package university.backend.controller;

import org.springframework.web.bind.annotation.*;
import university.backend.dto.TranscriptResponse;
import university.backend.service.TranscriptService;

@RestController
@RequestMapping("/api/transcripts")
public class TranscriptController {
    private final TranscriptService service;
    public TranscriptController(TranscriptService service){this.service=service;}

    @GetMapping("/student/{studentId}")
    public TranscriptResponse build(@PathVariable Long studentId){return service.build(studentId);}

    @GetMapping("/student/{studentId}/summary")
    public String summary(@PathVariable Long studentId){return service.summary(studentId);}
}