package solvas.rest.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Schema for a contract as defined in the api
 * @author Steven Bastiaens
 */
public class ApiContract extends ApiModel{

    @NotNull
    //required
    private LocalDate endDate;

    @NotNull
    //required
    private LocalDate startDate;

    //optional
    private int franchise;

    @Min(value = 1)
    //required
    private int insuranceCompany;

    //optional
    private int premium;

    @NotNull
    //required
    private String type;

    @Min(value = 1)
    //required
    private int vehicle;


}
