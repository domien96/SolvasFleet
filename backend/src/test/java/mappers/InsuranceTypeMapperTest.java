package mappers;

import org.junit.Test;
import shared.AbstractSolvasTest;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.mappers.InsuranceTypeMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.InsuranceType;


public class InsuranceTypeMapperTest extends AbstractSolvasTest<InsuranceType,String> {


    private InsuranceTypeMapper mapper;

    public InsuranceTypeMapperTest() {
        super(InsuranceType.class,String.class);
        mapper = new InsuranceTypeMapper(getDaoContext());
    }

    @Test
    public void convertInsuranceTypeToApiInsuranceType()
    {
        String result = mapper.convertToApiModel(getModel());

        //Compare (triviaal)
    }

    @Test
    public void convertApiInsuranceTypeToInsuranceType() throws DependantEntityNotFound, EntityNotFoundException {
        //Zorg ervoor dat bepaalde dao's correct gemocked worden (gebruik when(getDatacontext().getSpecificDao()....).thenReturn(...)) (check de mapper hiervoor)
        InsuranceType result = mapper.convertToModel(getApiModel());

        //Compare
    }

}
