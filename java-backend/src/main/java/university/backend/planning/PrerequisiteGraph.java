package university.backend.planning;

import java.util.*;

/** Small dependency graph used to validate course prerequisite chains. */
public class PrerequisiteGraph {
    private final Map<String, Set<String>> prerequisites = new HashMap<>();

    public void add(String course, String prerequisite) {
        prerequisites.computeIfAbsent(course, ignored -> new LinkedHashSet<>()).add(prerequisite);
    }

    public boolean satisfied(String course, Set<String> completed) {
        return completed.containsAll(prerequisites.getOrDefault(course, Set.of()));
    }

    public Set<String> missing(String course, Set<String> completed) {
        Set<String> missing = new LinkedHashSet<>(prerequisites.getOrDefault(course, Set.of()));
        missing.removeAll(completed);
        return missing;
    }

    public List<String> prerequisitesOf(String course) {
        return new ArrayList<>(prerequisites.getOrDefault(course, Set.of()));
    }
}
