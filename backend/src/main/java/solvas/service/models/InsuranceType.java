package solvas.service.models;

/**
 * Models a Insurance Type
 * Example types (non-exhaustive): BA, ...
 * @author Sjabasti
 */
public class InsuranceType extends Model {

    /**
     * Name of the insurance type
     */
    private String name;

    public InsuranceType() {
    } // Hibernate wants a no-arg constructor


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
