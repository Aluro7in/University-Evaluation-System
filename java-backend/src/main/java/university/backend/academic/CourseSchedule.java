package university.backend.academic;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/** Timetable slot with overlap detection. */
public record CourseSchedule(
        String courseCode,
        DayOfWeek day,
        LocalTime start,
        LocalTime end,
        String room
) {
    public CourseSchedule {
        Objects.requireNonNull(courseCode, "courseCode");
        Objects.requireNonNull(day, "day");
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (!end.isAfter(start)) throw new IllegalArgumentException("End time must be after start time");
    }

    public boolean overlaps(CourseSchedule other) {
        return day == other.day && start.isBefore(other.end) && other.start.isBefore(end);
    }

    public int durationMinutes() {
        return (int) java.time.Duration.between(start, end).toMinutes();
    }
}
