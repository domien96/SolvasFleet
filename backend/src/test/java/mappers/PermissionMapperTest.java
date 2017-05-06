package mappers;

import org.junit.Test;
import shared.AbstractSolvasTest;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiPermission;
import solvas.service.mappers.PermissionMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Permission;


public class PermissionMapperTest extends AbstractSolvasTest<Permission,ApiPermission> {

    private PermissionMapper mapper;

    public PermissionMapperTest() {
        super(Permission.class,ApiPermission.class);
        mapper = new PermissionMapper(getDaoContext());
    }

    @Test
    public void convertPermissionToApiPermission()
    {
        ApiPermission result = mapper.convertToApiModel(getModel());

        //Compare (triviaal)
    }

    @Test
    public void convertApiPermissionToPermission() throws DependantEntityNotFound, EntityNotFoundException {
        //Zorg ervoor dat bepaalde dao's correct gemocked worden (gebruik when(getDatacontext().getSpecificDao()....).thenReturn(...)) (check de mapper hiervoor)
        Permission result = mapper.convertToModel(getApiModel());

        //Compare
    }

}
