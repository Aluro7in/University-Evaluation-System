package university.backend.analysis;

import java.util.List;

/** Attendance calculations shared by student and admin reporting. */
public final class AttendanceAnalyzer {
    private AttendanceAnalyzer() {}

    public record AttendanceSummary(long total, long present, long absent, long excused, long late, double percentage) {
        public boolean meets(double threshold) { return percentage >= threshold; }
        public long missed() { return absent; }
    }

    public static AttendanceSummary summarize(List<String> statuses) {
        if (statuses == null) return new AttendanceSummary(0, 0, 0, 0, 0, 100.0);
        long present = statuses.stream().filter("PRESENT"::equalsIgnoreCase).count();
        long absent = statuses.stream().filter("ABSENT"::equalsIgnoreCase).count();
        long excused = statuses.stream().filter("EXCUSED"::equalsIgnoreCase).count();
        long late = statuses.stream().filter("LATE"::equalsIgnoreCase).count();
        long total = statuses.size();
        long counted = present + late;
        double percentage = total == 0 ? 100.0 : round(counted * 100.0 / total);
        return new AttendanceSummary(total, present, absent, excused, late, percentage);
    }

    public static double projectedPercentage(long currentPresent,long currentTotal,int remainingClasses) {
        if (remainingClasses < 0) throw new IllegalArgumentException("Remaining classes cannot be negative");
        long projectedTotal = currentTotal + remainingClasses;
        long projectedPresent = currentPresent + remainingClasses;
        if (projectedTotal == 0) return 100.0;
        return round(projectedPresent * 100.0 / projectedTotal);
    }

    public static int classesNeeded(long present,long total,double target) {
        if (target <= 0 || target > 100) throw new IllegalArgumentException("Target must be 0-100");
        int needed = 0;
        while (needed < 1000) {
            if (round((present + needed) * 100.0 / (total + needed)) >= target) return needed;
            needed++;
        }
        return -1;
    }

    private static double round(double value){return Math.round(value*100.0)/100.0;}
}