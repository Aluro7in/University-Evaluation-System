package university.backend.importer;

import java.util.List;

public record ImportResult(int accepted, int rejected, List<ImportIssue> issues) {
    public ImportResult {
        issues = issues == null ? List.of() : List.copyOf(issues);
        if (accepted < 0 || rejected < 0) throw new IllegalArgumentException("Import counts cannot be negative");
    }

    public boolean successful() { return rejected == 0; }
}
