package university.backend.analysis;

import university.backend.domain.enums.AcademicStanding;
import java.util.ArrayList;
import java.util.List;

/** Produces explainable academic alerts from deterministic rules. */
public final class AcademicAlertEngine {
    private AcademicAlertEngine() {}

    public record Alert(String code,String severity,String title,String message) {}

    public static List<Alert> evaluate(double gpa,double attendance,int failedCourses,boolean incomplete) {
        List<Alert> alerts=new ArrayList<>();
        if(gpa<1.0)alerts.add(new Alert("GPA_CRITICAL","HIGH","Critical GPA","Immediate academic support is recommended."));
        else if(gpa<2.0)alerts.add(new Alert("GPA_PROBATION","MEDIUM","Academic probation","Student should review an improvement plan."));
        if(attendance<60)alerts.add(new Alert("ATTENDANCE_CRITICAL","HIGH","Critical attendance","Attendance is below the minimum threshold."));
        else if(attendance<75)alerts.add(new Alert("ATTENDANCE_LOW","MEDIUM","Low attendance","Student should improve attendance."));
        if(failedCourses>=2)alerts.add(new Alert("MULTIPLE_FAILS","HIGH","Multiple failed courses","Course recovery or advisor intervention is recommended."));
        else if(failedCourses==1)alerts.add(new Alert("FAILED_COURSE","MEDIUM","Failed course","Retake or recovery planning may be required."));
        if(incomplete)alerts.add(new Alert("INCOMPLETE","LOW","Incomplete grade","Resolve outstanding assessment work."));
        if(alerts.isEmpty())alerts.add(new Alert("CLEAR","LOW","No academic alerts","Current rule-based indicators are within normal ranges."));
        return alerts;
    }

    public static AcademicStanding standing(double gpa){
        if(gpa>=3.5)return AcademicStanding.EXCELLENT;
        if(gpa>=3.0)return AcademicStanding.GOOD;
        if(gpa>=2.0)return AcademicStanding.SATISFACTORY;
        if(gpa>=1.0)return AcademicStanding.PROBATION;
        return AcademicStanding.AT_RISK;
    }
}