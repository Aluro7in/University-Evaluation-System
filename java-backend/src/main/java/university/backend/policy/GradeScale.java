package university.backend.policy;

import university.backend.domain.enums.GradeLetter;

public final class GradeScale {
    private GradeScale() {}

    public static double toGpa(int percentage) {
        validate(percentage);
        if (percentage >= 90) return 4.0;
        if (percentage >= 80) return 3.0;
        if (percentage >= 70) return 2.0;
        if (percentage >= 60) return 1.0;
        return 0.0;
    }

    public static GradeLetter toLetter(int percentage) {
        validate(percentage);
        if (percentage >= 90) return GradeLetter.A;
        if (percentage >= 80) return GradeLetter.B;
        if (percentage >= 70) return GradeLetter.C;
        if (percentage >= 60) return GradeLetter.D;
        return GradeLetter.F;
    }

    public static String band(int percentage) {
        validate(percentage);
        if (percentage >= 90) return "A / 90-100";
        if (percentage >= 80) return "B / 80-89";
        if (percentage >= 70) return "C / 70-79";
        if (percentage >= 60) return "D / 60-69";
        return "F / 0-59";
    }

    public static void validate(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
    }

    public static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}