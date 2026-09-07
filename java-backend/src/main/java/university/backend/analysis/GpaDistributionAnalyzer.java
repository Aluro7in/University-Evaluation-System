package university.backend.analysis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Groups GPAs into reporting bands used by dashboards. */
public final class GpaDistributionAnalyzer {
    private GpaDistributionAnalyzer() {}

    public static Map<String, Long> distribution(List<Double> gpas) {
        Map<String, Long> result = new LinkedHashMap<>();
        result.put("0.00-0.99", 0L);
        result.put("1.00-1.99", 0L);
        result.put("2.00-2.99", 0L);
        result.put("3.00-3.49", 0L);
        result.put("3.50-4.00", 0L);

        if (gpas == null) return result;

        for (Double value : gpas) {
            if (value == null) continue;
            String key = value < 1 ? "0.00-0.99"
                    : value < 2 ? "1.00-1.99"
                    : value < 3 ? "2.00-2.99"
                    : value < 3.5 ? "3.00-3.49"
                    : "3.50-4.00";
            result.compute(key, (k, old) -> old + 1);
        }
        return result;
    }

    public static double median(List<Double> values) {
        if (values == null || values.isEmpty()) return 0;
        List<Double> sorted = values.stream().filter(v -> v != null).sorted().toList();
        if (sorted.isEmpty()) return 0;
        int middle = sorted.size() / 2;
        if (sorted.size() % 2 == 1) return sorted.get(middle);
        return (sorted.get(middle - 1) + sorted.get(middle)) / 2.0;
    }

    public static double percentile(List<Double> values, double p) {
        List<Double> sorted = values == null ? List.of() : values.stream().filter(v -> v != null).sorted().toList();
        if (sorted.isEmpty()) return 0;
        double rank = (p / 100.0) * (sorted.size() - 1);
        int low = (int) Math.floor(rank);
        int high = (int) Math.ceil(rank);
        if (low == high) return sorted.get(low);
        double weight = rank - low;
        return sorted.get(low) * (1 - weight) + sorted.get(high) * weight;
    }
}