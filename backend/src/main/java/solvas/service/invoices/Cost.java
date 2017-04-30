package solvas.service.invoices;

import solvas.service.models.Contract;
import solvas.service.models.Tax;

import java.math.BigDecimal;

/**
 * @author Niko Strijbol
 */
public class Cost {

    private Contract contract;
    private Tax tax;
    // TODO
    private double commission = 0;

    public BigDecimal getTotal() {
        BigDecimal taxPercentage = BigDecimal.ONE.add(tax.getTax());
        BigDecimal commissionPercentage = BigDecimal.ONE.add(BigDecimal.valueOf(commission));
        BigDecimal premium = BigDecimal.valueOf(contract.getPremium());
        return premium.multiply(commissionPercentage).multiply(taxPercentage);
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Contract getContract() {
        return contract;
    }

    public Tax getTax() {
        return tax;
    }

    public double getCommission() {
        return commission;
    }
}