package university.backend.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Plans a student's next courses while respecting simple prerequisite sets. */
public final class EnrollmentPlanner {
    private EnrollmentPlanner(){}

    public record CourseOption(String code,int credits,List<String> prerequisites) {}

    public static List<String> eligible(List<CourseOption> options,Set<String> completed,int maxCredits){
        List<String> selected=new ArrayList<>();
        Set<String> safeCompleted=completed==null?Set.of():new HashSet<>(completed);
        int credits=0;
        if(options==null)return selected;
        for(CourseOption option:options){
            if(option==null||credits+option.credits()>maxCredits)continue;
            Set<String> req=new HashSet<>(option.prerequisites()==null?List.of():option.prerequisites());
            if(safeCompleted.containsAll(req)){selected.add(option.code());credits+=option.credits();}
        }
        return selected;
    }

    public static int totalCredits(List<CourseOption> options){
        return options==null?0:options.stream().mapToInt(CourseOption::credits).sum();
    }
}