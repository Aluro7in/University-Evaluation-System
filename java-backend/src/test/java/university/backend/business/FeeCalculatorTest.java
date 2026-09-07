package university.backend.business;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class FeeCalculatorTest {
    @Test void calculatesScholarshipAndBalance() {
        var calc = new FeeCalculator();
        var result = calc.calculate(new BigDecimal("1000"), new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("0.10"), new BigDecimal("20"));
        assertEquals(new BigDecimal("1070.00"), result.payable());
        assertEquals(new BigDecimal("70.00"), calc.balance(result.payable(), new BigDecimal("1000")));
    }
}
