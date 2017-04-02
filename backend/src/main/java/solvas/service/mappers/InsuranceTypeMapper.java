package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.service.models.InsuranceType;

import java.util.Collection;

/**
 * Mapper between InsuranceType and it's String representation
 */
@Component
public class InsuranceTypeMapper extends AbstractMapper<InsuranceType,String> {

    /**
     * TODO document
     *
     * @param daoContext
     */
    public InsuranceTypeMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public InsuranceType convertToModel(String api) {
        Collection<InsuranceType> types = daoContext.getInsuranceTypeDao().findByName(api);
        InsuranceType type;
        if (types.size()==0){
            type = new InsuranceType();
            type.setId(0);
            type.setName(api);
            return daoContext.getInsuranceTypeDao().save(type);
        } else {
            return types.iterator().next();
        }
    }


    @Override
    public String convertToApiModel(InsuranceType model) {
        return model.getName();
    }
}