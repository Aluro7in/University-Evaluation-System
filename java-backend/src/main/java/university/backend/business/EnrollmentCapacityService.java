package university.backend.business;

import java.util.Map;

/** Capacity and wait-list decisions for course enrollment. */
public class EnrollmentCapacityService {
    public record Decision(String state, int remaining, boolean waitListAvailable) {}

    public Decision decide(int capacity, int enrolled, int waitList, boolean allowWaitList) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        if (enrolled < 0 || waitList < 0) throw new IllegalArgumentException("Counts cannot be negative");
        int remaining = Math.max(0, capacity - enrolled);
        if (remaining > 0) return new Decision("OPEN", remaining, allowWaitList);
        if (allowWaitList) return new Decision("WAITLIST", 0, true);
        return new Decision("FULL", 0, false);
    }

    public Map<String, Object> utilization(int capacity, int enrolled) {
        if (capacity <= 0 || enrolled < 0) throw new IllegalArgumentException("Invalid capacity data");
        double percent = Math.round(Math.min(100, enrolled * 100.0 / capacity) * 100.0) / 100.0;
        return Map.of("capacity", capacity, "enrolled", enrolled, "utilization", percent, "remaining", Math.max(0, capacity - enrolled));
    }
}
