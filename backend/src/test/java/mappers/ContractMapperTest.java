package mappers;

import org.junit.Test;
import shared.AbstractSolvasTest;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.ContractMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Contract;


public class ContractMapperTest extends AbstractSolvasTest<Contract,ApiContract> {

    private ContractMapper mapper;

    public ContractMapperTest() {
        super(Contract.class,ApiContract.class);
        mapper = new ContractMapper(getDaoContext());
    }

    @Test
    public void convertContractToApiContract()
    {
        ApiContract result = mapper.convertToApiModel(getModel());

        //Compare (triviaal)
    }

    @Test
    public void convertApiContractToContract() throws DependantEntityNotFound, EntityNotFoundException {
        //Zorg ervoor dat bepaalde dao's correct gemocked worden (gebruik when(getDatacontext().getSpecificDao()....).thenReturn(...)) (check de mapper hiervoor)
        Contract result = mapper.convertToModel(getApiModel());
        //Compare
    }

}
