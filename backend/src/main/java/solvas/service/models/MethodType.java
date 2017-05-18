package solvas.service.models;

/**
 * This is a helper class when auditing. It shows all methods that could have
 *  changed a model.
 */
public enum MethodType {

    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    ARCHIVE("archive");

    /**
     * Keep a text representation as a object needs to be parsed to json later on.
     */
    private String text;

    MethodType(String text) {
        this.text = text;
    }

    /**
     * returns string value of a enum element
     * @return string value of a enum element
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