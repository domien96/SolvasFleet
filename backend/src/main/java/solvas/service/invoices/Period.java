package solvas.service.invoices;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Class representing a period in time
 */
public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     *
     * @param startDate Start of the period
     * @param endDate End of the period
     */
    public Period(@NotNull LocalDate startDate, @NotNull LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String toString() {
        return startDate+" - "+endDate;
    }
}
