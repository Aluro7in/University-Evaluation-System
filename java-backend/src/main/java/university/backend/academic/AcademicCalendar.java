package university.backend.academic;

import java.time.LocalDate;
import java.util.Objects;

/** Academic calendar value object used by scheduling and semester workflows. */
public record AcademicCalendar(
        String semester,
        LocalDate registrationStart,
        LocalDate registrationEnd,
        LocalDate classesStart,
        LocalDate classesEnd,
        LocalDate examinationStart,
        LocalDate examinationEnd
) {
    public AcademicCalendar {
        Objects.requireNonNull(semester, "semester");
        if (registrationStart.isAfter(registrationEnd)) throw new IllegalArgumentException("Invalid registration window");
        if (classesStart.isAfter(classesEnd)) throw new IllegalArgumentException("Invalid class window");
        if (examinationStart.isAfter(examinationEnd)) throw new IllegalArgumentException("Invalid examination window");
    }

    public boolean registrationOpen(LocalDate date) {
        return !date.isBefore(registrationStart) && !date.isAfter(registrationEnd);
    }

    public boolean classesRunning(LocalDate date) {
        return !date.isBefore(classesStart) && !date.isAfter(classesEnd);
    }

    public boolean examinationsRunning(LocalDate date) {
        return !date.isBefore(examinationStart) && !date.isAfter(examinationEnd);
    }
}
