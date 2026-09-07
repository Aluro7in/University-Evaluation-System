package university.backend.dto;
public record EvaluationResponse(double gpa,double averagePercentage,String academicStanding,
                                 String calculationPolicy,int courseCount,int totalCredits) {}