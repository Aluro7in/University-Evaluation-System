package university.backend;

import org.junit.jupiter.api.Test;
import university.backend.analysis.*;
import university.backend.policy.*;
import static org.junit.jupiter.api.Assertions.*;

class BusinessRuleBoundary5Test {

    @Test
    void validates5a() {
        assertEquals(4.0, GradeScale.toGpa(90));
        assertEquals(3.0, GradeScale.toGpa(80));
        assertEquals(2.0, GradeScale.toGpa(70));
        assertEquals(1.0, GradeScale.toGpa(60));
        assertEquals(0.0, GradeScale.toGpa(59));
    }

    @Test
    void validates5b() {
        assertEquals("A / 90-100", GradeScale.band(95));
        assertEquals("B / 80-89", GradeScale.band(85));
        assertEquals("C / 70-79", GradeScale.band(75));
        assertEquals("D / 60-69", GradeScale.band(65));
        assertEquals("F / 0-59", GradeScale.band(50));
    }

    @Test
    void validates5c() {
        assertThrows(IllegalArgumentException.class, () -> GradeScale.validate(-1));
        assertThrows(IllegalArgumentException.class, () -> GradeScale.validate(101));
        assertTrue(AcademicRules.passes(60));
        assertFalse(AcademicRules.passes(59));
    }

    @Test
    void validates5d() {
        assertNotNull(new AcademicProgressReport("a",90,80,70).summary());
        assertNotNull(new GpaBucketReport("b",88,77,66).metrics());
        assertNotNull(new CourseOutcomeReport("c",95,90,85).csvRow());
        assertNotNull(new CreditAuditReport("d",1,2,3).toJsonLike());
    }

    @Test
    void validates5e() {
        var report = new AttendanceRiskReport("attendance",90,80,70);
        assertTrue(report.average() > 0);
        assertTrue(report.primaryShare() >= 0);
        assertTrue(report.primaryShare() <= 100);
        assertTrue(report.targetMet(50));
        assertFalse(report.targetMet(100));
    }

    @Test
    void validates5f() {
        assertTrue(SearchScorer.score("cse101","cse101") > SearchScorer.score("cse","mgt101"));
        assertTrue(SearchScorer.score("cse","cse101") > 0);
    }

    @Test
    void validates5g() {
        var trend = GpaTrendAnalyzer.analyze(java.util.List.of(
                new GpaTrendAnalyzer.Point("2025-FALL",2.4),
                new GpaTrendAnalyzer.Point("2026-SPRING",2.9),
                new GpaTrendAnalyzer.Point("2026-FALL",3.2)
        ));
        assertEquals("IMPROVING", trend.direction());
        assertTrue(trend.change() > 0);
        assertEquals(2.83, trend.average(), 0.01);
    }

    @Test
    void validates5h() {
        var distribution = GpaDistributionAnalyzer.distribution(
                java.util.List.of(0.5,1.2,2.4,3.2,3.8));
        assertEquals(1, distribution.get("0.00-0.99"));
        assertEquals(1, distribution.get("1.00-1.99"));
        assertEquals(1, distribution.get("2.00-2.99"));
        assertEquals(1, distribution.get("3.00-3.49"));
        assertEquals(1, distribution.get("3.50-4.00"));
    }

    @Test
    void validates5i() {
        var load = CourseLoadAnalyzer.analyze(java.util.List.of(4,4,3,3));
        assertEquals(14, load.credits());
        assertEquals(4, load.courses());
        assertEquals("STANDARD", load.level());
        assertFalse(load.recommendation().isBlank());
    }

    @Test
    void validates5j() {
        var performance = PerformanceAnalyzer.summarize(java.util.List.of(95,90,80,70));
        assertEquals(83.75, performance.average(), 0.01);
        assertEquals(4, performance.passes());
        assertEquals(0, performance.fails());
        assertEquals("STRONG", performance.pattern());
    }

    @Test
    void validates5k() {
        var progress = CreditCompletionAnalyzer.analyze(90,120);
        assertEquals(30, progress.remaining());
        assertEquals(75.0, progress.percentage(), 0.01);
        assertEquals("FINAL_STAGE", progress.status());
        assertEquals(2, CreditCompletionAnalyzer.semestersRequired(30,16));
    }

    @Test
    void validates5l() {
        var risk = WorkloadRiskAnalyzer.analyze(20,1.2,65,2);
        assertEquals("HIGH", risk.level());
        assertTrue(risk.score() > 0);
        assertFalse(risk.factors().isEmpty());
    }

    @Test
    void validates5m() {
        var alerts = AcademicAlertEngine.evaluate(1.5,70,1,true);
        assertTrue(alerts.size() >= 3);
        assertTrue(alerts.stream().anyMatch(a -> a.code().equals("GPA_PROBATION")));
        assertTrue(alerts.stream().anyMatch(a -> a.code().equals("INCOMPLETE")));
    }

    @Test
    void validates5n() {
        var attendance = AttendanceAnalyzer.summarize(
                java.util.List.of("PRESENT","PRESENT","LATE","ABSENT","EXCUSED"));
        assertEquals(5, attendance.total());
        assertEquals(2, attendance.present());
        assertEquals(1, attendance.late());
        assertEquals(60.0, attendance.percentage(), 0.01);
        assertTrue(attendance.meets(60));
    }

    @Test
    void validates5o() {
        var outcome = TranscriptStatistics.calculate(
                java.util.List.of(95,85,50,70),
                java.util.List.of(4,3,3,4));
        assertEquals(4, outcome.courses());
        assertEquals(14, outcome.credits());
        assertEquals(3, outcome.passes());
        assertEquals(1, outcome.fails());
        assertFalse(TranscriptStatistics.isComplete(outcome));
        assertEquals(75.0, TranscriptStatistics.passRate(outcome), 0.01);
    }
}