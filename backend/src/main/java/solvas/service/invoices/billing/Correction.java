package solvas.service.invoices.billing;

import solvas.service.invoices.Cost;
import solvas.service.models.Invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A correction on the billing invoice. This is a base class.
 *
 * @author Niko Strijbol
 */
public abstract class Correction extends Cost {

    private static final int PERCENTAGE_SCALE = 2;

    private long days;

    protected long totalPeriod;

    /**
     * The total number of days the correction should use to calculate the amount to pay.
     * For example:
     * An addition on the 10th -> days = 20 (1 month - 10)
     * A stopped contract on the 10th -> days = 10 (was 10 days active)
     *
     * @return The number of days.
     */
    protected long getDays() {
        return days;
    }

    /**
     * Set the number of days. See the getter for a description.
     *
     * @param days The number of days.
     */
    public void setDays(long days) {
        this.days = days;
    }

    /**
     * The multiplier for the calculated value. For example:
     * An addition -> multiplier = 1 (you need to pay more)
     * A deletion -> multiplier = -1 (you get money).
     *
     * @return The multiplier.
     */
    protected abstract int multiplier();

    /**
     * The number of days the contract was actieve in the invoice period.
     *
     * @return The number of days.
     */
    public long daysActive() {
        return days;
    }

    @Override
    public BigDecimal getTotal() {
        return super.getTotal().multiply(percentage()).multiply(new BigDecimal(multiplier()));
    }

    /**
     * The percentage of days to the total. See {@link #getDays()} for more information.
     *
     * @return The percentage.
     */
    private BigDecimal percentage() {
        BigDecimal total = new BigDecimal(totalPeriod);
        BigDecimal part = new BigDecimal(days);
        return part.divide(total, PERCENTAGE_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * The percentage of active days in comparison to the total invoice period.
     *
     * @return The percentage.
     */
    public BigDecimal percentageActive() {
        BigDecimal total = new BigDecimal(totalPeriod);
        BigDecimal part = new BigDecimal(daysActive());
        return part.divide(total, PERCENTAGE_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Sets the total period from the invoice.
     *
     * @param invoice The invoice.
     */
    public void setTotalPeriod(Invoice invoice) {
        this.totalPeriod = ChronoUnit.DAYS.between(invoice.getStartDate(), invoice.getEndDate());
    }

    /**
     * The name to display on the invoice. E.g. 'Stopped contract'.
     *
     * @return The name.
     */
    public abstract String getCorrectionName();

    /**
     * Get the date of the correction.
     *
     * @return The date.
     */
    public abstract LocalDateTime getEventDate();
}