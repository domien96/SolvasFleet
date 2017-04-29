package solvas.service.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InvoiceItem {
    private Contract contract;
    private Invoice invoice;
    private InvoiceItemType invoiceItemType;
    private LocalDate startDate;
    private LocalDate endDate;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceItemType getInvoiceItemType() {
        return invoiceItemType;
    }

    public void setInvoiceItemType(InvoiceItemType invoiceItemType) {
        this.invoiceItemType = invoiceItemType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getNumberOfDays() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
