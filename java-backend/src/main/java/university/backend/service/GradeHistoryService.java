package university.backend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * In-memory event stream for grade changes.
 * The database grade remains the source of truth; this service provides
 * a lightweight history stream for API responses and observability.
 */
@Service
public class GradeHistoryService {
    public record Entry(String enrollmentId,int oldGrade,int newGrade,String actor,String timestamp) {}

    private final List<Entry> events = Collections.synchronizedList(new ArrayList<>());

    public void record(Long enrollmentId,int oldGrade,int newGrade,String actor){
        events.add(new Entry(String.valueOf(enrollmentId),oldGrade,newGrade,
                actor==null?"system":actor,Instant.now().toString()));
        if(events.size()>1000) events.removeFirst();
    }

    public List<Entry> recent(int limit){
        int safe=Math.max(1,Math.min(limit,100));
        synchronized(events){
            return events.reversed().stream().limit(safe).toList();
        }
    }

    public long size(){return events.size();}
}