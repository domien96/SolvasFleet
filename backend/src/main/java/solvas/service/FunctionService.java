package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.dao.FunctionDao;
import solvas.rest.api.models.ApiFunction;
import solvas.service.mappers.FunctionMapper;
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
            FunctionDao modelDao,
            FunctionMapper mapper) {
        super(modelDao, mapper);
    }
}
