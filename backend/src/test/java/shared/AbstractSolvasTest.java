package shared;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.*;
import solvas.service.models.Model;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractSolvasTest<T extends Model, E > {
    private Class<T> class1;
    private Class<E> class2;
    private T model;
    private E apiModel;

    @Mock
    private DaoContext daoContext;

    @Mock
    private VehicleDao vehicleDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private UserDao userDao;
    @Mock
    private VehicleTypeDao vehicleTypeDao;
    @Mock
    private InvoiceDao invoiceDao;
    @Mock
    private FleetDao fleetDao;
    @Mock
    private FunctionDao functionDao;
    @Mock
    private CompanyDao companyDao;
    @Mock
    private ContractDao contractDao;
    @Mock
    private TaxDao taxDao;
    @Mock
    private PermissionDao permissionDao;
    @Mock
    private InsuranceTypeDao insuranceTypeDao;
    @Mock
    private FleetSubscriptionDao fleetSubscriptionDao;


    public AbstractSolvasTest(Class<T> class1,Class<E> class2)
    {
        this.class1=class1;
        this.class2=class2;
    }

    @Before
    public void setUp()
    {
        model=random(class1);
        apiModel=random(class2);

        when(daoContext.getVehicleDao()).thenReturn(vehicleDao);
        when(daoContext.getRoleDao()).thenReturn(roleDao);
        when(daoContext.getUserDao()).thenReturn(userDao);
        when(daoContext.getVehicleTypeDao()).thenReturn(vehicleTypeDao);
        when(daoContext.getInvoiceDao()).thenReturn(invoiceDao);
        when(daoContext.getFleetDao()).thenReturn(fleetDao);
        when(daoContext.getFunctionDao()).thenReturn(functionDao);
        when(daoContext.getCompanyDao()).thenReturn(companyDao);
        when(daoContext.getContractDao()).thenReturn(contractDao);
        when(daoContext.getTaxDao()).thenReturn(taxDao);
        when(daoContext.getPermissionDao()).thenReturn(permissionDao);
        when(daoContext.getInsuranceTypeDao()).thenReturn(insuranceTypeDao);
        when(daoContext.getFleetSubscriptionDao()).thenReturn(fleetSubscriptionDao);
    }

    public T getModel()
    {
        return model;
    }

    public E getApiModel()
    {
        return apiModel;
    }

    public DaoContext getDaoContext()
    {
        return daoContext;
    }


}

