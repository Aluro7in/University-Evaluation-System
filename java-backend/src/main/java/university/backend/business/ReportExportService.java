package university.backend.business;

import java.time.Instant;
import java.util.List;

/** Produces stable CSV/JSON-like exports for integration and auditing. */
public class ReportExportService {
    public String toCsv(List<String[]> rows) {
        if (rows == null || rows.isEmpty()) return "";
        return rows.stream().map(this::csvRow).reduce((a, b) -> a + "\n" + b).orElse("");
    }

    public String csvRow(String[] values) {
        if (values == null) return "";
        return java.util.Arrays.stream(values).map(this::escape).reduce((a, b) -> a + "," + b).orElse("");
    }

    public String exportEnvelope(String reportName, int rows, String checksum) {
        if (reportName == null || reportName.isBlank()) throw new IllegalArgumentException("Report name is required");
        return "{\"report\":\"" + escapeJson(reportName) + "\",\"rows\":" + rows
                + ",\"checksum\":\"" + escapeJson(checksum) + "\",\"generatedAt\":\"" + Instant.now() + "\"}";
    }

    private String escape(String value) {
        if (value == null) return "";
        String normalized = value.replace("\"", "\"\"");
        return normalized.contains(",") || normalized.contains("\n") || normalized.contains("\"") ? "\"" + normalized + "\"" : normalized;
    }

    private String escapeJson(String value) { return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\""); }
}
