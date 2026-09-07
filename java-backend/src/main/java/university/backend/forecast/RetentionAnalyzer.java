package university.backend.forecast;

public class RetentionAnalyzer {
    public RetentionScore score(double gpa, double attendance, double creditCompletion, int failedCourses) {
        double risk = 0;
        risk += clamp((2.5 - gpa) * 22, 0, 45);
        risk += clamp((85 - attendance) * 0.5, 0, 25);
        risk += clamp((75 - creditCompletion) * 0.25, 0, 15);
        risk += clamp(failedCourses * 5, 0, 15);
        risk = Math.round(risk * 10.0) / 10.0;
        String level = risk >= 70 ? "HIGH" : risk >= 40 ? "MEDIUM" : "LOW";
        String explanation = level.equals("HIGH") ? "Multiple academic indicators require intervention" : level.equals("MEDIUM") ? "Monitor progress and offer advising" : "No immediate retention risk detected";
        return new RetentionScore(risk, level, explanation);
    }

    private double clamp(double value, double min, double max) { return Math.max(min, Math.min(max, value)); }
}
