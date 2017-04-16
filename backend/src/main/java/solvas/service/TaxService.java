package solvas.service;

import org.springframework.stereotype.Service;
import solvas.persistence.api.dao.TaxDao;
import solvas.rest.api.models.ApiTax;
import solvas.service.mappers.TaxMapper;
import solvas.service.models.Tax;

/**
 * Created by steve on 16/04/2017.
 */
@Service
public class TaxService extends AbstractService<Tax,ApiTax> {

    /**
     * Contruct an TaxService
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    public TaxService(TaxDao modelDao, TaxMapper mapper) {
        super(modelDao, mapper);
    }


}
