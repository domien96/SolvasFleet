package mappers;

import org.junit.Test;
import shared.AbstractSolvasTest;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiTax;
import solvas.service.mappers.TaxMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Tax;


public class TaxMapperTest extends AbstractSolvasTest<Tax,ApiTax> {

    private TaxMapper mapper;

    public TaxMapperTest() {
        super(Tax.class,ApiTax.class);
        mapper = new TaxMapper(getDaoContext());
    }

    @Test
    public void convertTaxtoApiTax()
    {
        ApiTax result = mapper.convertToApiModel(getModel());

        //Compare (triviaal)
    }

    @Test
    public void convertApiTaxToTax() throws DependantEntityNotFound, EntityNotFoundException {
        //Zorg ervoor dat bepaalde dao's correct gemocked worden (gebruik when(getDatacontext().getSpecificDao()....).thenReturn(...)) (check de mapper hiervoor)
        Tax result = mapper.convertToModel(getApiModel());

        //Compare
    }

}
