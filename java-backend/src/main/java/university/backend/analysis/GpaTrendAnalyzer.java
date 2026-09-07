package university.backend.analysis;

import java.util.ArrayList;
import java.util.List;

/** Calculates descriptive trends from semester GPA observations. */
public final class GpaTrendAnalyzer {
    private GpaTrendAnalyzer() {}

    public record Point(String semester, double gpa) {}
    public record Trend(double first, double last, double change, String direction, double average) {}

    public static Trend analyze(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return new Trend(0, 0, 0, "NO_DATA", 0);
        }
        double total = 0;
        for (Point point : points) total += point.gpa();
        double first = points.getFirst().gpa();
        double last = points.getLast().gpa();
        double change = round(last - first);
        String direction = change > 0.05 ? "IMPROVING" : change < -0.05 ? "DECLINING" : "STABLE";
        return new Trend(first, last, change, direction, round(total / points.size()));
    }

    public static List<Double> movingAverage(List<Double> values, int window) {
        if (values == null || values.isEmpty() || window <= 0) return List.of();
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            int start = Math.max(0, i - window + 1);
            double sum = 0;
            for (int j = start; j <= i; j++) sum += values.get(j);
            result.add(round(sum / (i - start + 1)));
        }
        return result;
    }

    public static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}