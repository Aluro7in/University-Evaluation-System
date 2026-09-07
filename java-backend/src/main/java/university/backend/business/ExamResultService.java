package university.backend.business;

import java.util.List;

/** Combines continuous assessment and final examination marks. */
public class ExamResultService {
    public record Assessment(String name, double score, double weight) {
        public Assessment {
            if (score < 0 || score > 100) throw new IllegalArgumentException("Score must be between 0 and 100");
            if (weight <= 0 || weight > 100) throw new IllegalArgumentException("Weight must be in (0,100]");
        }
    }

    public record Result(double internal, double exam, double finalScore, String letter, boolean passed) {}

    public Result calculate(List<Assessment> internals, double finalExam, double passMark) {
        if (internals == null || internals.isEmpty()) throw new IllegalArgumentException("Internal assessments required");
        if (finalExam < 0 || finalExam > 100) throw new IllegalArgumentException("Final exam must be between 0 and 100");
        double totalWeight = internals.stream().mapToDouble(Assessment::weight).sum();
        if (Math.abs(totalWeight - 60.0) > 0.0001) throw new IllegalArgumentException("Internal weights must total 60%");
        double internal = internals.stream().mapToDouble(a -> a.score() * a.weight() / 100.0).sum();
        double score = round(internal + finalExam * 0.40);
        return new Result(round(internal), finalExam, score, letter(score), score >= passMark);
    }

    private String letter(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
    private double round(double value) { return Math.round(value * 100.0) / 100.0; }
}
