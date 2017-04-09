package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.Dao;
import solvas.rest.api.models.ApiFunction;
import solvas.service.mappers.AbstractMapper;
import solvas.service.models.Function;

/**
 * Service class for functions
 */
@Service
public class FunctionService extends AbstractService<Function, ApiFunction> {
    /**
     * Create instance
     * @param modelDao Dao to find functions
     * @param mapper Mapper to convert ApiFunction into Function
     */
    @Autowired
    public FunctionService(
            Dao<Function> modelDao,
            AbstractMapper<Function, ApiFunction> mapper) {
        super(modelDao, mapper);
    }
}
