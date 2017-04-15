package solvas.rest.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Schema for a Invoice as defined in the api
 * @author Steven Bastiaens
 */
public class ApiInvoice extends ApiModel {

    @NotNull
    //required
    private LocalDate endDate;

    @NotNull
    //required
    private LocalDate startDate;

    @NotNull
    //optional
    private boolean paid;

    @Min(value = 1)
    //required
    private int company;

    @Min(value = 0)
    //optional
    private long totalAmount;

    @Min(value = 1)
    //required
    private int type;


}
