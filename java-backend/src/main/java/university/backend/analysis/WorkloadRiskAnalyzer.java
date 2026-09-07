package university.backend.analysis;

import java.util.List;

/** Combines workload, GPA and attendance into a transparent risk score. */
public final class WorkloadRiskAnalyzer {
    private WorkloadRiskAnalyzer(){}

    public record Result(double score,String level,List<String> factors) {}

    public static Result analyze(int credits,double gpa,double attendance,int failedCourses){
        double score=0;
        java.util.ArrayList<String> factors=new java.util.ArrayList<>();
        if(credits>=20){score+=0.3;factors.add("heavy-course-load");}
        else if(credits>=16){score+=0.15;factors.add("elevated-course-load");}
        if(gpa<2){score+=0.35;factors.add("low-gpa");}
        else if(gpa<2.5){score+=0.15;factors.add("borderline-gpa");}
        if(attendance<60){score+=0.25;factors.add("critical-attendance");}
        else if(attendance<75){score+=0.12;factors.add("low-attendance");}
        if(failedCourses>0){score+=Math.min(0.25,failedCourses*0.1);factors.add("failed-courses");}
        score=Math.min(1,score);
        String level=score>=0.65?"HIGH":score>=0.3?"MEDIUM":"LOW";
        return new Result(round(score),level,List.copyOf(factors));
    }

    private static double round(double value){return Math.round(value*100.0)/100.0;}
}