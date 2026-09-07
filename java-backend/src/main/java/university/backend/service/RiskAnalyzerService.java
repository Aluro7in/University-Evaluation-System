package university.backend.service;

import org.springframework.stereotype.Service;
import university.backend.domain.model.StudentRiskProfile;

@Service
public class RiskAnalyzerService {

    public StudentRiskProfile classify(String studentId,double gpa,double attendance,
                                       int failedCourses,boolean incomplete){
        int score=0;
        if(gpa<1.0)score+=4;else if(gpa<2.0)score+=2;else if(gpa<2.5)score++;
        if(attendance<60)score+=4;else if(attendance<75)score+=2;else if(attendance<85)score++;
        score+=Math.min(failedCourses*2,4);
        if(incomplete)score++;

        String risk=score>=7?"HIGH":score>=3?"MEDIUM":"LOW";
        return new StudentRiskProfile(
                studentId,risk,gpa,attendance,failedCourses,incomplete,recommendation(risk));
    }

    public String recommendation(String risk){
        return switch(risk){
            case "HIGH" -> "Academic advisor meeting recommended within 7 days.";
            case "MEDIUM" -> "Monitor attendance and create a course recovery plan.";
            default -> "Continue the current academic plan.";
        };
    }

    public boolean requiresAdvisor(StudentRiskProfile profile){
        return "HIGH".equals(profile.riskLevel());
    }

    public boolean isPassingTrend(double previousGpa,double currentGpa){
        return currentGpa>=previousGpa;
    }
}