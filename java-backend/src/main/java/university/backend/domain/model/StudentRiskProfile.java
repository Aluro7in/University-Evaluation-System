package university.backend.domain.model;

public record StudentRiskProfile(String studentId,String riskLevel,double gpa,double attendance,
                                 int failedCourses,boolean incomplete,String recommendation) {
    public boolean requiresAdvisor(){return "HIGH".equals(riskLevel);}
    public boolean needsMonitoring(){return "MEDIUM".equals(riskLevel)||requiresAdvisor();}
    public double riskScore(){
        double score=0;
        if(gpa<1)score+=0.45;else if(gpa<2)score+=0.25;else if(gpa<2.5)score+=0.1;
        if(attendance<60)score+=0.4;else if(attendance<75)score+=0.2;else if(attendance<85)score+=0.1;
        score+=Math.min(failedCourses*0.1,0.2);
        if(incomplete)score+=0.1;
        return Math.min(score,1.0);
    }
}