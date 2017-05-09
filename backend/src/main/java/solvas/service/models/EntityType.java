package solvas.service.models;

/**
 * Created by steve on 09/05/2017.
 */
public enum EntityType {

    VEHICLES("vehicles"),USERS("users");


    private String text;

    EntityType(String text) {
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
    public static EntityType fromString(String text) {
        for (EntityType b : EntityType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
