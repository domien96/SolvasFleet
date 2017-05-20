package solvas.service.models;

/**
 * All different types of invoices.
 * Created by domien on 15/04/2017.
 */
public enum InvoiceType {
    BILLING("billing"),
    PAYMENT("payment"),
    CORRECTION("correction");

    private String text;

    InvoiceType(String text) {
        this.text = text;
    }

    /**
     * returns string value of a enum element
     * @return
     */
    public String getText() {
        return this.text;
    }

    /**
     * Converts a text to a enum value
     * @param text
     * @return enum  value of th text, if the enum value doesn't exist it returns null
     */
    public static InvoiceType fromString(String text) {
        for (InvoiceType b : InvoiceType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
