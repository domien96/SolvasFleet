package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

/**
 * Fleet in the API layer
 */
public class ApiFleet extends ApiModel {

    private int company;

    @Min(value = 1)
    private int facturationPeriod;  // In months

    @Min(value = 1)
    private int paymentPeriod;// In months

    @NotEmpty
    private String name;

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getFacturationPeriod() {
        return facturationPeriod;
    }

    public void setFacturationPeriod(int facturationPeriod) {
        this.facturationPeriod = facturationPeriod;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }
}