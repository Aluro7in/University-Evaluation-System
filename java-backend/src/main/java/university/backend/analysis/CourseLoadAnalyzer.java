package university.backend.analysis;

import java.util.List;

/** Evaluates the academic load represented by registered course credits. */
public final class CourseLoadAnalyzer {
    private CourseLoadAnalyzer() {}

    public record Load(int credits, int courses, String level, String recommendation) {}

    public static Load analyze(List<Integer> credits) {
        int total = credits == null ? 0 : credits.stream().filter(c -> c != null).mapToInt(Integer::intValue).sum();
        int courses = credits == null ? 0 : (int) credits.stream().filter(c -> c != null).count();
        String level = total >= 20 ? "VERY_HEAVY" : total >= 16 ? "HEAVY" : total >= 12 ? "STANDARD" : total >= 6 ? "LIGHT" : "MINIMAL";
        String recommendation = switch(level) {
            case "VERY_HEAVY" -> "Advisor approval recommended.";
            case "HEAVY" -> "Monitor workload and attendance.";
            case "STANDARD" -> "Normal academic load.";
            case "LIGHT" -> "Consider completing additional credits.";
            default -> "Check enrollment plan.";
        };
        return new Load(total, courses, level, recommendation);
    }

    public static boolean exceedsLimit(List<Integer> credits, int maxCredits) {
        return analyze(credits).credits() > maxCredits;
    }

    public static double averageCredits(List<Integer> credits) {
        if (credits == null || credits.isEmpty()) return 0;
        return credits.stream().filter(c -> c != null).mapToInt(Integer::intValue).average().orElse(0);
    }
}