package solvas.rest.api.mappers;

import solvas.models.Model;
import solvas.persistence.Dao;
import solvas.persistence.EntityNotFoundException;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.rest.api.models.ApiModel;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;

/**
 * Class parameter T : class of the domain model class.
 * Class parameter E : class of the api model class.
 * Created by steve on 11/03/2017.
 */
public abstract class AbstractMapper<T extends Model, E> {

    /**
     * TODO document
     * @param api
     * @return
     * @throws FieldNotFoundException If the fields to be copied are misconfigured
     */
    public abstract T convertToModel(E api) throws EntityNotFoundException, FieldNotFoundException;

    /**
     * TODO document
     * @param model
     * @throws FieldNotFoundException If the fields to be copied are misconfigured
     */
    public abstract E convertToApiModel(T model) throws FieldNotFoundException;


    /**
     * Copy attribute from source (if set), to target
     * @param target Entity to copy to
     * @param src Entity to copy from
     * @param attributes attributes to copy
     * @throws FieldNotFoundException If a field wasn't found or was inaccessible
     */

    protected void copyAttributes(Object target, Object src, String ...attributes) throws FieldNotFoundException {
        for(String attribute: attributes) {
            copyNotNull(target, src, attribute);
        }
    }

    /**
     * Copy attribute from source (if set), to target
     * @param target Entity to copy to
     * @param source Entity to copy from
     * @param name attribute to copy
     * @throws FieldNotFoundException If field wasn't found or was inaccessible
     */
    private void copyNotNull(Object target, Object source, String name) throws FieldNotFoundException {
        try {
            Field targetField = target.getClass().getDeclaredField(name);
            Field sourceField = source.getClass().getDeclaredField(name);
            if (sourceField.get(source) != null) {
                targetField.set(target, sourceField.get(source));
            }
        } catch  (NoSuchFieldException | IllegalAccessException e) {
            throw new FieldNotFoundException(e);
        }
    }
}
