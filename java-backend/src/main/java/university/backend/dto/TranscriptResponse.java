package university.backend.dto;
import java.util.List;
public record TranscriptResponse(StudentResponse student,List<GradeResponse> grades,double gpa,double averagePercentage,
                                 int totalCredits,String policy,String academicStanding,String generatedAt) {}