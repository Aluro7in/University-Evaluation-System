package university.backend.analysis;

import java.util.List;

/** Computes transcript-level summary statistics. */
public final class TranscriptStatistics {
    private TranscriptStatistics(){}

    public record Statistics(int courses,int credits,int passes,int fails,double average,int distinctionCount) {}

    public static Statistics calculate(List<Integer> marks,List<Integer> credits){
        int count=marks==null?0:marks.size();
        int totalCredits=credits==null?0:credits.stream().mapToInt(Integer::intValue).sum();
        int passes=0,fails=0,distinctions=0;double total=0;
        if(marks!=null)for(int mark:marks){
            total+=mark;
            if(mark>=60)passes++;else fails++;
            if(mark>=90)distinctions++;
        }
        return new Statistics(count,totalCredits,passes,fails,count==0?0:total/count,distinctions);
    }

    public static boolean isComplete(Statistics s){return s.courses()>0&&s.fails()==0;}
    public static double passRate(Statistics s){return s.courses()==0?0:s.passes()*100.0/s.courses();}
}