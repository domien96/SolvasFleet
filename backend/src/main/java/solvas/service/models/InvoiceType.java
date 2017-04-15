package solvas.service.models;

/**
 * All different types of invoices.
 * Created by domien on 15/04/2017.
 */
public enum InvoiceType {
    BILLING("billing")
    ,PAYMENT("payment");

    private String text;

    InvoiceType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static InvoiceType fromString(String text) {
        for (InvoiceType b : InvoiceType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
