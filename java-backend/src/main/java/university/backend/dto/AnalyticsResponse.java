package university.backend.dto;
public record AnalyticsResponse(long students,long activeStudents,long courses,long enrollments,long gradedEnrollments,
                                double averageGpa,double averagePercentage,long studentsAtRisk,long studentsOnProbation,
                                long deanListEligible) {}