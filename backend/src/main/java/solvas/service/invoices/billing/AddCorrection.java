package solvas.service.invoices.billing;

import java.time.LocalDateTime;

/**
 * A correction where a contract has been added to the fleet. This means the customer has to pay money.
 *
 * @author Niko Strijbol
 */
public class AddCorrection extends Correction {

    @Override
    protected int multiplier() {
        return 1;
    }

    @Override
    public String getCorrectionName() {
        return "Toevoeging";
    }

    @Override
    public LocalDateTime getEventDate() {
        return getContract().getStartDate();
    }
}