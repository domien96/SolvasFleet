package mappers;

import org.junit.Test;
import shared.AbstractSolvasTest;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiFunction;
import solvas.service.mappers.FunctionMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Function;


public class FunctionMapperTest extends AbstractSolvasTest<Function,ApiFunction> {

    private FunctionMapper mapper;

    public FunctionMapperTest() {
        super(Function.class,ApiFunction.class);
        mapper = new FunctionMapper(getDaoContext());
    }

    @Test
    public void convertFunctionToApiFunction()
    {
        ApiFunction result = mapper.convertToApiModel(getModel());

        //Compare (triviaal)
    }

    @Test
    public void convertApiFunctionToFunction() throws DependantEntityNotFound, EntityNotFoundException {
        //Zorg ervoor dat bepaalde dao's correct gemocked worden (gebruik when(getDatacontext().getSpecificDao()....).thenReturn(...)) (check de mapper hiervoor)
        Function result = mapper.convertToModel(getApiModel());

        //Compare
    }

}
