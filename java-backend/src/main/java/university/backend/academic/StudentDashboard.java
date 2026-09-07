package university.backend.academic;

import java.util.List;

/** Consolidated read model for dashboard and REST responses. */
public record StudentDashboard(
        long studentId,
        String studentNumber,
        String name,
        String program,
        double gpa,
        double attendanceRate,
        int creditsAttempted,
        int creditsEarned,
        String academicStanding,
        List<String> alerts
) {
    public StudentDashboard {
        alerts = alerts == null ? List.of() : List.copyOf(alerts);
    }

    public double completionRate() {
        if (creditsAttempted <= 0) return 0;
        return round(creditsEarned * 100.0 / creditsAttempted);
    }

    public boolean needsAttention() {
        return gpa < 2.0 || attendanceRate < 75.0 || !alerts.isEmpty();
    }

    private static double round(double value) { return Math.round(value * 100.0) / 100.0; }
}
