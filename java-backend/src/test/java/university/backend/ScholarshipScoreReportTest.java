package university.backend;

import org.junit.jupiter.api.Test;
import university.backend.report.ScholarshipScoreReport;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipScoreReportTest {
    @Test void createsUsefulMetrics(){
        ScholarshipScoreReport report=new ScholarshipScoreReport("demo",90,80,70);
        assertEquals("demo",report.subject());
        assertTrue(report.total()>0);
        assertTrue(report.average()>0);
        assertTrue(report.primaryShare()>=0);
        assertTrue(report.primaryShare()<=100);
    }

    @Test void exposesStableSummary(){
        ScholarshipScoreReport report=new ScholarshipScoreReport("demo",95,90,85);
        assertTrue(report.summary().contains("demo"));
        assertNotNull(report.csvRow());
        assertNotNull(report.toJsonLike());
        assertFalse(report.flags().isEmpty());
    }

    @Test void combinesMetrics(){
        ScholarshipScoreReport first=new ScholarshipScoreReport("demo",50,60,70);
        ScholarshipScoreReport second=new ScholarshipScoreReport("demo",10,20,30);
        ScholarshipScoreReport combined=first.add(second);
        assertEquals(120,combined.total(),0.001);
        assertTrue(combined.equalsMetrics(new ScholarshipScoreReport("demo",60,80,100),0.001));
        assertEquals(120,combined.scale(1).total(),0.001);
    }

    @Test void handlesEmptyAndTargets(){
        ScholarshipScoreReport empty=ScholarshipScoreReport.empty("empty");
        assertEquals(0,empty.total(),0.001);
        assertTrue(empty.targetMet(0));
        assertTrue(empty.gapFrom(50)>=0);
        assertTrue(empty.level().equals("LOW") || empty.level().equals("WATCH"));
    }
}