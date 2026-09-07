package university.backend;

import org.junit.jupiter.api.Test;
import university.backend.report.CreditAuditReport;
import static org.junit.jupiter.api.Assertions.*;

class CreditAuditReportTest {
    @Test void createsUsefulMetrics(){
        CreditAuditReport report=new CreditAuditReport("demo",90,80,70);
        assertEquals("demo",report.subject());
        assertTrue(report.total()>0);
        assertTrue(report.average()>0);
        assertTrue(report.primaryShare()>=0);
        assertTrue(report.primaryShare()<=100);
    }

    @Test void exposesStableSummary(){
        CreditAuditReport report=new CreditAuditReport("demo",95,90,85);
        assertTrue(report.summary().contains("demo"));
        assertNotNull(report.csvRow());
        assertNotNull(report.toJsonLike());
        assertFalse(report.flags().isEmpty());
    }

    @Test void combinesMetrics(){
        CreditAuditReport first=new CreditAuditReport("demo",50,60,70);
        CreditAuditReport second=new CreditAuditReport("demo",10,20,30);
        CreditAuditReport combined=first.add(second);
        assertEquals(120,combined.total(),0.001);
        assertTrue(combined.equalsMetrics(new CreditAuditReport("demo",60,80,100),0.001));
        assertEquals(120,combined.scale(1).total(),0.001);
    }

    @Test void handlesEmptyAndTargets(){
        CreditAuditReport empty=CreditAuditReport.empty("empty");
        assertEquals(0,empty.total(),0.001);
        assertTrue(empty.targetMet(0));
        assertTrue(empty.gapFrom(50)>=0);
        assertTrue(empty.level().equals("LOW") || empty.level().equals("WATCH"));
    }
}