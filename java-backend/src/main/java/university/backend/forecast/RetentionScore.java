package university.backend.forecast;

public record RetentionScore(double score, String level, String explanation) {
    public boolean highRisk() { return score >= 70; }
}
