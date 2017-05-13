package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.InsuranceType;

import java.util.Optional;

/**
 * Mapper between InsuranceType and it's String representation
 */
@Component
public class InsuranceTypeMapper extends AbstractMapper<InsuranceType,String> {

    /**
     * Create a mapper between InsuranceType and insurance type in the api as a string
     *
     * @param daoContext The context for Dao's
     */
    public InsuranceTypeMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public InsuranceType convertToModel(String api) throws EntityNotFoundException {
        Optional<InsuranceType> type = daoContext.getInsuranceTypeDao().findByName(api);
        if (type.isPresent()){
            return type.get();
        } else {
            throw new EntityNotFoundException("Could not find insurance type");
        }
    }


    @Override
    public String convertToApiModel(InsuranceType model) {
        return model.getName();
    }
}