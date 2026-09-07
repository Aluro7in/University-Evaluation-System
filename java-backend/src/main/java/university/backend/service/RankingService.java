package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.dto.RankingEntry;
import university.backend.repository.GradeRepository;

import java.util.*;

@Service
public class RankingService {
    private final StudentService students;
    private final GradeRepository grades;

    public RankingService(StudentService students,GradeRepository grades){
        this.students=students;this.grades=grades;
    }

    @Transactional(readOnly=true)
    public List<RankingEntry> top(int limit){
        int bounded=Math.max(1,Math.min(limit,100));

        List<RankingEntry> sorted=students.allEntities().stream()
                .filter(s->Boolean.TRUE.equals(s.getActive()))
                .map(s->{
                    var gs=grades.findByEnrollmentStudentId(s.getId());
                    double gpa=gs.stream().mapToDouble(g->g.getGpa()).average().orElse(0);
                    int credits=gs.stream().mapToInt(g->g.getEnrollment().getCourse().getCredits()).sum();
                    return new RankingEntry(s.getStudentId(),s.getName(),s.getType().name(),
                            Math.round(gpa*100.0)/100.0,credits,0);
                })
                .sorted(Comparator.comparingDouble(RankingEntry::gpa).reversed()
                        .thenComparing(Comparator.comparingInt(RankingEntry::totalCredits).reversed())
                        .thenComparing(RankingEntry::studentName))
                .toList();

        List<RankingEntry> result=new ArrayList<>();
        double previous=Double.NaN;
        int rank=0;

        for(int i=0;i<sorted.size();i++){
            RankingEntry row=sorted.get(i);
            if(i==0||Double.compare(previous,row.gpa())!=0){
                rank=i+1;
                previous=row.gpa();
            }
            result.add(new RankingEntry(row.studentId(),row.studentName(),row.studentType(),
                    row.gpa(),row.totalCredits(),rank));
        }
        return result.stream().limit(bounded).toList();
    }
}