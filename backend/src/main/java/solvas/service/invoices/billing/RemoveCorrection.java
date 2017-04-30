package solvas.service.invoices.billing;

import java.time.LocalDateTime;

/**
 * A correction where a contract has terminated in the fleet. This means Solvas owes the customer money.
 *
 * @author Niko Strijbol
 */
public class RemoveCorrection extends Correction {

    @Override
    protected int multiplier() {
        return -1;
    }

    @Override
    public String getCorrectionName() {
        return "Annulatie";
    }

    @Override
    public LocalDateTime getEventDate() {
        return getContract().getEndDate();
    }

    @Override
    public long daysActive() {
        return totalPeriod - getDays();
    }
}