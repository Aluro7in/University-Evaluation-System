package university.backend.analysis;

import java.util.List;

/** Provides deterministic checks for suspicious or inconsistent grade records. */
public final class GradeAuditCalculator {
    private GradeAuditCalculator() {}

    public record AuditResult(boolean valid,int min,int max,double average,int outOfRange,long exactDuplicates) {}

    public static AuditResult audit(List<Integer> grades) {
        if(grades==null||grades.isEmpty())return new AuditResult(true,0,0,0,0,0);
        int min=101,max=-1,out=0;double total=0;
        java.util.Map<Integer,Integer> freq=new java.util.HashMap<>();
        for(Integer grade:grades){
            if(grade==null){out++;continue;}
            min=Math.min(min,grade);max=Math.max(max,grade);total+=grade;
            if(grade<0||grade>100)out++;
            freq.merge(grade,1,Integer::sum);
        }
        long duplicates=freq.values().stream().filter(v->v>1).mapToLong(v->v-1).sum();
        return new AuditResult(out==0,min,max,total/grades.size(),out,duplicates);
    }

    public static boolean plausibleDifference(int oldGrade,int newGrade){
        return Math.abs(newGrade-oldGrade)<=100;
    }
}