package solvas.service.models;


/**
 * Created by steve on 09/05/2017.
 */
public class EntityType {

    private EntityType(){};

    public static String fromClass(Class clazz) {
        return clazz.getName();

    }


    public static Class toClass(String clazz) throws ClassNotFoundException {
        return Class.forName(clazz);

    }




}
