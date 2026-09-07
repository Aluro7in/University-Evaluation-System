package university.backend.business;

import java.util.LinkedHashMap;
import java.util.Map;

/** Attendance policy and warning escalation rules. */
public class AttendancePolicyService {
    public record AttendanceSummary(int present, int late, int absent, int excused) {
        public int total() { return present + late + absent + excused; }
        public double rate() {
            return total() == 0 ? 100 : round((present + late * 0.5 + excused) * 100.0 / total());
        }
        private static double round(double value) { return Math.round(value * 100.0) / 100.0; }
    }

    public String status(AttendanceSummary summary) {
        double rate = summary.rate();
        if (rate >= 90) return "EXCELLENT";
        if (rate >= 80) return "GOOD";
        if (rate >= 75) return "WARNING";
        return "CRITICAL";
    }

    public Map<String, Object> recommendation(AttendanceSummary summary) {
        double rate = summary.rate();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("rate", rate);
        result.put("status", status(summary));
        result.put("requiresAdvisor", rate < 75);
        result.put("eligibleForFinalExam", rate >= 65);
        result.put("recommendedAction", rate < 65 ? "RESTRICT" : rate < 75 ? "ADVISE" : rate < 80 ? "MONITOR" : "NONE");
        return result;
    }
}
