package solvas.service.models;


/**
 * This is a helper class when auditing. It shows all entities. A entity is a model.
 *  Using entity type, the exact model is known. The id's of different models
 *  can be the same. A id is only unique in its class scope.
 */
public class EntityType {

    /**
     * Empty private constructor because all methods are static
     */
    private EntityType(){};


    /**
     *  Translate a class to a string
     * @param clazz the class of which a string needs to be retrieved
     * @return the name of the class
     */
    public static String fromClass(Class clazz) {
        return clazz.getName();
    }

    /**
     *  Translate a string to a class
     * @param clazz the string name of a class
     * @return the class with the given name
     * @throws ClassNotFoundException exception is thrown when the class is not found
     */
    public static Class toClass(String clazz) throws ClassNotFoundException {
        return Class.forName(clazz);

    }




}
