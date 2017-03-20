package solvas.rest.api.mappers;

import solvas.models.Model;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.exceptions.DependantEntityNotFound;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;

import java.lang.reflect.Field;

/**
 * Mapper between a Model in the persistence layer and it's representation in the REST API
 *
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
     *
     * @param api An model according to the REST api
     * @return A Model in the persistence layer
     */
    public abstract T convertToModel(E api) throws DependantEntityNotFound;

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
     * Copy attribute from source (if set), to target
     *
     * @param target Entity to copy to
     * @param source Entity to copy from
     * @param name   attribute to copy
     * @throws FieldNotFoundException If field wasn't found or was inaccessible
     */
    private void copyNotNull(Object target, Object source, String name) {
        try {
            Field targetField = target.getClass().getDeclaredField(name);
            Field sourceField = source.getClass().getDeclaredField(name);
            if (sourceField.get(source) != null) {
                targetField.set(target, sourceField.get(source));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new FieldNotFoundException(e);
        }
    }


    /**
     * Copy attributes that were marked as shared in the constructor
     * @param target The entity to copy to
     * @param source The entity to copy from
     * @throws FieldNotFoundException
     */
    protected void copySharedAttributes(Object target, Object source) {
        copyAttributes(target, source, sharedAttributes);
    }
}
