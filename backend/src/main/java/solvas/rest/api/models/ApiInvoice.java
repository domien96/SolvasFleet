package solvas.rest.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Schema for a contract as defined in the Invoice
 * @author Steven Bastiaens
 */
public class ApiInvoice {

    @NotNull
    //required
    private LocalDate endDate;

    @NotNull
    //required
    private LocalDate startDate;

    @NotNull
    //optional
    private boolean payed;

    @Min(value = 1)
    //required
    private int company;

    @Min(value = 0)
    //optional
    private long totalAmount;

    @Min(value = 1)
    //required
    private int type;

    @Min(value = 1)
    //required
    private int vehicle;



}
