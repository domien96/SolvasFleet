package solvas.service.models;

public enum InvoiceItemType {
    PAYMENT(1),REPAYMENT(-1);

    private final int factor;

    InvoiceItemType(int factor) {
        this.factor = factor;
    }
}
