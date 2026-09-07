package university.backend.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AttendanceTrendReport is a deterministic backend reporting component.
 *
 * It keeps calculation rules outside controllers and persistence code so that
 * the web API can expose the same business semantics consistently. The report
 * accepts normalized numeric inputs, produces bounded metrics, and provides
 * human-readable summaries for admin dashboards and audits.
 */
public final class AttendanceTrendReport {

    private final double primary;
    private final double secondary;
    private final double tertiary;
    private final String subject;

    public AttendanceTrendReport(String subject, double primary, double secondary, double tertiary) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Report subject is required");
        }
        if (primary < 0 || secondary < 0 || tertiary < 0) {
            throw new IllegalArgumentException("Report values cannot be negative");
        }
        this.subject = subject.trim();
        this.primary = primary;
        this.secondary = secondary;
        this.tertiary = tertiary;
    }

    public String subject() {
        return subject;
    }

    public double primary() {
        return primary;
    }

    public double secondary() {
        return secondary;
    }

    public double tertiary() {
        return tertiary;
    }

    public double total() {
        return round(primary + secondary + tertiary);
    }

    public double average() {
        return round(total() / 3.0);
    }

    public double primaryShare() {
        return ratio(primary, total());
    }

    public double secondaryShare() {
        return ratio(secondary, total());
    }

    public double tertiaryShare() {
        return ratio(tertiary, total());
    }

    public String level() {
        double value = average();
        if (value >= 90) return "EXCELLENT";
        if (value >= 75) return "GOOD";
        if (value >= 60) return "WATCH";
        return "LOW";
    }

    public boolean healthy(double threshold) {
        return average() >= threshold;
    }

    public double gapFrom(double target) {
        return round(target - average());
    }

    public boolean targetMet(double target) {
        return average() >= target;
    }

    public List<String> flags() {
        List<String> flags = new ArrayList<>();
        if (primary < secondary) flags.add("PRIMARY_BELOW_SECONDARY");
        if (secondary < tertiary) flags.add("SECONDARY_BELOW_TERTIARY");
        if (average() < 60) flags.add("LOW_OVERALL");
        if (average() >= 90) flags.add("DISTINCTION");
        if (flags.isEmpty()) flags.add("NO_RULE_FLAGS");
        return flags;
    }

    public Map<String, Double> metrics() {
        Map<String, Double> metrics = new LinkedHashMap<>();
        metrics.put("primary", primary);
        metrics.put("secondary", secondary);
        metrics.put("tertiary", tertiary);
        metrics.put("total", total());
        metrics.put("average", average());
        metrics.put("primaryShare", primaryShare());
        metrics.put("secondaryShare", secondaryShare());
        metrics.put("tertiaryShare", tertiaryShare());
        return metrics;
    }

    public String summary() {
        return subject + " | average=" + format(average()) + " | level=" + level();
    }

    public String csvRow() {
        return subject + "," + format(primary) + "," + format(secondary) + "," +
                format(tertiary) + "," + format(average()) + "," + level();
    }

    public static AttendanceTrendReport empty(String subject) {
        return new AttendanceTrendReport(subject, 0, 0, 0);
    }

    public static AttendanceTrendReport from(List<Double> values, String subject) {
        if (values == null || values.isEmpty()) return empty(subject);
        double first = values.size() > 0 ? safe(values.get(0)) : 0;
        double second = values.size() > 1 ? safe(values.get(1)) : 0;
        double third = values.size() > 2 ? safe(values.get(2)) : 0;
        return new AttendanceTrendReport(subject, first, second, third);
    }

    public AttendanceTrendReport add(AttendanceTrendReport other) {
        if (other == null) return this;
        return new AttendanceTrendReport(subject, primary + other.primary, secondary + other.secondary,
                tertiary + other.tertiary);
    }

    public AttendanceTrendReport scale(double factor) {
        if (factor < 0) throw new IllegalArgumentException("Scale factor cannot be negative");
        return new AttendanceTrendReport(subject, primary * factor, secondary * factor, tertiary * factor);
    }

    public boolean equalsMetrics(AttendanceTrendReport other, double tolerance) {
        if (other == null || tolerance < 0) return false;
        return Math.abs(primary - other.primary) <= tolerance
                && Math.abs(secondary - other.secondary) <= tolerance
                && Math.abs(tertiary - other.tertiary) <= tolerance;
    }

    public String toJsonLike() {
        return "{\"subject\":\"" + subject + "\",\"primary\":" + format(primary)
                + ",\"secondary\":" + format(secondary)
                + ",\"tertiary\":" + format(tertiary)
                + ",\"average\":" + format(average())
                + ",\"level\":\"" + level() + "\"}";
    }

    private static double safe(Double value) {
        return value == null || value.isNaN() || value.isInfinite() ? 0 : Math.max(0, value);
    }

    private static double ratio(double value, double total) {
        if (total <= 0) return 0;
        return round(value * 100.0 / total);
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private static String format(double value) {
        return String.format(java.util.Locale.ROOT, "%.2f", value);
    }
}