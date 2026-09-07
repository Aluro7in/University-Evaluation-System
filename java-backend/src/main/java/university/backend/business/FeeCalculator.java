package university.backend.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/** Calculates academic fees without tying the rule to a web controller. */
public class FeeCalculator {
    public record FeeBreakdown(BigDecimal tuition, BigDecimal lab, BigDecimal library,
                               BigDecimal scholarship, BigDecimal penalty, BigDecimal payable) {}

    public FeeBreakdown calculate(BigDecimal tuition, BigDecimal lab, BigDecimal library,
                                  BigDecimal scholarshipRate, BigDecimal penalty) {
        requireNonNegative(tuition, "tuition");
        requireNonNegative(lab, "lab");
        requireNonNegative(library, "library");
        requireNonNegative(penalty, "penalty");
        if (scholarshipRate.compareTo(BigDecimal.ZERO) < 0 || scholarshipRate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Scholarship rate must be between 0 and 1");
        }
        BigDecimal gross = tuition.add(lab).add(library);
        BigDecimal scholarship = tuition.multiply(scholarshipRate);
        BigDecimal payable = gross.subtract(scholarship).add(penalty).setScale(2, RoundingMode.HALF_UP);
        return new FeeBreakdown(tuition, lab, library, scholarship.setScale(2, RoundingMode.HALF_UP), penalty, payable);
    }

    public boolean settled(BigDecimal payable, BigDecimal paid) {
        requireNonNegative(payable, "payable");
        requireNonNegative(paid, "paid");
        return paid.compareTo(payable) >= 0;
    }

    public BigDecimal balance(BigDecimal payable, BigDecimal paid) {
        requireNonNegative(payable, "payable");
        requireNonNegative(paid, "paid");
        return payable.subtract(paid).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    public Map<String, String> status(BigDecimal payable, BigDecimal paid) {
        BigDecimal balance = balance(payable, paid);
        String state = balance.signum() == 0 ? "PAID" : paid.signum() == 0 ? "UNPAID" : "PARTIAL";
        return Map.of("state", state, "balance", balance.toPlainString());
    }

    private void requireNonNegative(BigDecimal value, String field) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(field + " cannot be negative");
        }
    }
}
