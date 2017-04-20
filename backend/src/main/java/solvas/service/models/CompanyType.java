package solvas.service.models;

/**
 * All different types of companies.
 * TODO refactor make a super class with getText and setText
 * Created by sjabasti on 15/04/2017.
 */
public enum CompanyType {

    CUSTOMER("Customer"),LEASINGCOMPANY("LeasingCompany"),INSURANCECOMPANY("InsuranceCompany");


    private String text;

    CompanyType(String text) {
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
    public static CompanyType fromString(String text) {
        for (CompanyType b : CompanyType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
