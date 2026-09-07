package university.backend.domain.model;

public record CourseProgress(String courseCode,String courseName,int credits,Integer percentage,
                             double gpa,boolean passed,boolean finalized) {
    public String status(){
        if(percentage==null)return "IN_PROGRESS";
        if(!finalized)return passed?"PASS_PENDING_FINALIZATION":"FAIL_PENDING_FINALIZATION";
        return passed?"COMPLETED":"FAILED";
    }
    public double weightedPoints(){return gpa*credits;}
    public boolean needsRetake(){return percentage!=null&&!passed;}
    public String displayGrade(){return percentage==null?"N/A":percentage+"%";}
}