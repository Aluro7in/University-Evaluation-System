package university.backend.forecast;

import java.util.List;

/** Conservative trend projection; intentionally deterministic and explainable. */
public class PerformanceForecastService {
    public GpaForecast forecast(List<Double> historicalGpa, double nextTermTarget) {
        if (historicalGpa == null || historicalGpa.isEmpty()) return new GpaForecast(0, nextTermTarget, "NEW", nextTermTarget);
        double current = historicalGpa.get(historicalGpa.size() - 1);
        double baseline = historicalGpa.stream().mapToDouble(Double::doubleValue).average().orElse(current);
        double projected = clamp((current * 0.6) + (baseline * 0.2) + (nextTermTarget * 0.2), 0, 4);
        double delta = round(projected - current);
        String direction = delta > 0.05 ? "IMPROVING" : delta < -0.05 ? "DECLINING" : "STABLE";
        return new GpaForecast(round(current), round(projected), direction, delta);
    }

    private static double clamp(double v, double min, double max) { return Math.max(min, Math.min(max, v)); }
    private static double round(double v) { return Math.round(v * 100.0) / 100.0; }
}
