package university.backend.planning;

import university.backend.academic.CourseSchedule;
import java.util.ArrayList;
import java.util.List;

public final class ConflictDetector {
    private ConflictDetector() {}

    public static List<String> findConflicts(List<CourseSchedule> schedules) {
        List<String> conflicts = new ArrayList<>();
        if (schedules == null) return conflicts;
        for (int i = 0; i < schedules.size(); i++) {
            for (int j = i + 1; j < schedules.size(); j++) {
                CourseSchedule left = schedules.get(i);
                CourseSchedule right = schedules.get(j);
                if (left.overlaps(right)) conflicts.add(left.courseCode() + " conflicts with " + right.courseCode());
            }
        }
        return conflicts;
    }
}
