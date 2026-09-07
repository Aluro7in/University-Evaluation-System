package university.backend.domain.model;

import university.backend.domain.enums.AcademicStanding;

public record GpaSummary(double gpa,double averagePercentage,int totalCredits,
                          int gradedCourses,AcademicStanding standing) {
    public boolean deanListEligible(){return gpa>=3.5&&totalCredits>=12;}
    public boolean probation(){return standing==AcademicStanding.PROBATION||standing==AcademicStanding.AT_RISK;}
    public boolean excellent(){return standing==AcademicStanding.EXCELLENT;}
    public String label(){return String.format("GPA %.2f | %s | %d credits",gpa,standing,totalCredits);}
    public String riskMessage(){
        return switch(standing){
            case EXCELLENT -> "Student is performing at an excellent level.";
            case GOOD -> "Student is maintaining good academic performance.";
            case SATISFACTORY -> "Student is meeting minimum academic expectations.";
            case PROBATION -> "Student should be placed on an academic improvement plan.";
            case AT_RISK -> "Immediate academic support is recommended.";
        };
    }
}