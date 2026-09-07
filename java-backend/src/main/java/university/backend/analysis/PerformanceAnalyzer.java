package university.backend.analysis;

import java.util.List;

/** Detects performance patterns without making unsupported predictions. */
public final class PerformanceAnalyzer {
    private PerformanceAnalyzer() {}

    public record Summary(double average,double highest,double lowest,int passes,int fails,String pattern) {}

    public static Summary summarize(List<Integer> marks) {
        if (marks == null || marks.isEmpty()) return new Summary(0,0,0,0,0,"NO_DATA");
        int passes=0,fails=0;
        double total=0,highest=Double.MIN_VALUE,lowest=Double.MAX_VALUE;
        for(int mark:marks){
            if(mark>=60) passes++; else fails++;
            total+=mark; highest=Math.max(highest,mark); lowest=Math.min(lowest,mark);
        }
        double average=total/marks.size();
        String pattern=average>=85?"DISTINCTION":average>=70?"STRONG":average>=60?"PASSING":"NEEDS_SUPPORT";
        return new Summary(round(average),highest,lowest,passes,fails,pattern);
    }

    public static double improvement(List<Integer> older,List<Integer> newer) {
        if(older==null||older.isEmpty()||newer==null||newer.isEmpty())return 0;
        double a=older.stream().mapToInt(Integer::intValue).average().orElse(0);
        double b=newer.stream().mapToInt(Integer::intValue).average().orElse(0);
        return round(b-a);
    }

    private static double round(double v){return Math.round(v*100.0)/100.0;}
}