package university.backend.business;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Deterministic relevance scoring for admin/student search. */
public class StudentSearchEngine {
    public record StudentIndex(long id, String studentId, String name, String major, String type) {}
    public record Match(StudentIndex student, double score) {}

    public List<Match> search(List<StudentIndex> index, String query) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        return index.stream()
                .map(student -> new Match(student, score(student, q)))
                .filter(match -> match.score() > 0)
                .sorted(Comparator.comparingDouble(Match::score).reversed()
                        .thenComparing(match -> match.student().studentId()))
                .toList();
    }

    private double score(StudentIndex student, String q) {
        if (q.isBlank()) return 1;
        double score = 0;
        score += field(student.studentId(), q, 50);
        score += field(student.name(), q, 35);
        score += field(student.major(), q, 20);
        score += field(student.type(), q, 10);
        return score;
    }

    private double field(String value, String query, double exactWeight) {
        if (value == null) return 0;
        String normalized = value.toLowerCase(Locale.ROOT);
        if (normalized.equals(query)) return exactWeight;
        if (normalized.startsWith(query)) return exactWeight * 0.75;
        if (normalized.contains(query)) return exactWeight * 0.5;
        return 0;
    }
}
