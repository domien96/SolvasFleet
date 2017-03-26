package solvas.rest.api.mappers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import solvas.models.Model;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.exceptions.DependantEntityNotFound;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;

import java.lang.reflect.Field;
import solvas.persistence.api.EntityNotFoundException;

/**
 * Mapper between a Model in the domain layer and it's representation in the REST API
 * This class is needed because the API isn't a perfect match with our domain layer
 * @param <T> class of the domain model class.
 * @param <E> class of the api model class.
 *            Created by steve on 11/03/2017.
 */
public abstract class AbstractMapper<T extends Model, E> {
    private final String[] sharedAttributes;

    protected final DaoContext daoContext;

    /**
     * @param daoContext DaoContext
     */
    public AbstractMapper(DaoContext daoContext) {
        this(daoContext, new String[0]);
    }

    /**
     * @param daoContext DaoContext
     * @param sharedAttributes Varargs of attributes that can safely be copied when mapping in both ways
     */
    public AbstractMapper(DaoContext daoContext, String ...sharedAttributes) {
        this.daoContext = daoContext;
        this.sharedAttributes = sharedAttributes;
    }

    /**
     * Convert an ApiModel to a Model in the persistence layer
<<<<<<< HEAD
     * @param api A model according to the REST api
=======
     *
     * @param api An model according to the REST api
>>>>>>> master
     * @return A Model in the persistence layer
     */
    public abstract T convertToModel(E api) throws DependantEntityNotFound, EntityNotFoundException;

    /**
     * Convert a Model in the persistence layer to an ApiModel
     *
     * @param model A Model in the persistence layer
     * @return An model according to the REST api
     */
    public abstract E convertToApiModel(T model);


    /**
     * Copy attribute from source (if set), to target
     *
     * @param target     Entity to copy to
     * @param src        Entity to copy from
     * @param attributes attributes to copy
     * @throws FieldNotFoundException If a field wasn't found or was inaccessible
     */

    protected void copyAttributes(Object target, Object src, String... attributes) {
        for (String attribute : attributes) {
            copyNotNull(target, src, attribute);
        }
    }


    /**
     * Searches a field of a class, if it is not found, it will search in its supperclasses
     * @param clazz base class of which the field must be searched
     * @param name attribute to copy
     * @return Field with name
     * @throws FieldNotFoundException If field wasn't found
     */
    private Field findFieldInSupper(Class<?> clazz, String name) {
        Class<?> current = clazz;
        do {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {}
        } while((current = current.getSuperclass()) != null);
        throw new FieldNotFoundException("Field "+name+"not found");
    }


    /**
     * Copy attribute from source (if set), to target
     *
     * @param target Entity to copy to
     * @param source Entity to copy from
     * @param name   attribute to copy
     * @throws FieldNotFoundException If field wasn't found or was inaccessible
     */
    private void copyNotNull(Object target, Object source, String name) {
        Field targetField = findFieldInSupper(target.getClass(),name);
        Field sourceField = findFieldInSupper(source.getClass(),name);
        try {
            targetField.setAccessible(true);
            sourceField.setAccessible(true);
            if (sourceField.get(source) != null) {
                targetField.set(target, sourceField.get(source));
            }
        } catch (IllegalAccessException e) {
            throw new FieldNotFoundException(e);
        } finally {
            targetField.setAccessible(false);
            sourceField.setAccessible(false);
        }
    }


    /**
     * Copy attributes that were marked as shared in the constructor
     * @param target The entity to copy to
     * @param source The entity to copy from
     */
    protected void copySharedAttributes(Object target, Object source) {
        copyAttributes(target, source, sharedAttributes);
    }
}
