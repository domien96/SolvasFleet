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
     */
    public abstract T convertToModel(E api) throws EntityNotFoundException, FieldNotFoundException;

    /**
     * TODO document
     * @param model
     * @return
     */
    public abstract E convertToApiModel(T model) throws FieldNotFoundException;



    protected void copyAttributes(Object target, Object src, String ...attributes) throws FieldNotFoundException {
        for(String attribute: attributes) {
            copyNotNull(target, src, attribute);
        }
    }

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
