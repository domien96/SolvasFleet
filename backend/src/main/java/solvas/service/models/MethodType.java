package solvas.service.models;

/**
 * Created by steve on 09/05/2017.
 */
public enum MethodType {

    INSERT("insert"),UPDATE("update"),DELETE("delete");


    private String text;

    MethodType(String text) {
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
    public static MethodType fromString(String text) {
        for (MethodType b : MethodType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}