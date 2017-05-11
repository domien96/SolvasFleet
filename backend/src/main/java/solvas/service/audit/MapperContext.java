package solvas.service.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.mappers.*;
import solvas.service.models.*;

import java.util.HashMap;

/**
 * Created by steve on 11/05/2017.
 */
@Component
public class MapperContext {

    private CompanyMapper companyMapper;
    private ContractMapper contractMapper;
    private FleetMapper fleetMapper;
    private FunctionMapper functionMapper;
    private InsuranceTypeMapper insuranceTypeMapper;
    private InvoiceMapper invoiceMapper;
    private PermissionMapper permissionMapper;
    private RoleMapper roleMapper;
    private TaxMapper taxMapper;
    private UserMapper userMapper;
    private VehicleMapper vehicleMapper;
    private VehicleTypeMapper vehicleTypeMapper;
    private HashMap<Class,AbstractMapper> mapperForClass = new HashMap<>();

    @Autowired
    public MapperContext(CompanyMapper companyMapper, ContractMapper contractMapper, FleetMapper fleetMapper,
                         FunctionMapper functionMapper, InsuranceTypeMapper insuranceTypeMapper,
                         InvoiceMapper invoiceMapper, PermissionMapper permissionMapper, RoleMapper roleMapper,
                         TaxMapper taxMapper, UserMapper userMapper, VehicleMapper vehicleMapper,
                         VehicleTypeMapper vehicleTypeMapper) {
        this.companyMapper = companyMapper;
        this.contractMapper = contractMapper;
        this.fleetMapper = fleetMapper;
        this.functionMapper = functionMapper;
        this.insuranceTypeMapper = insuranceTypeMapper;
        this.invoiceMapper = invoiceMapper;
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
        this.taxMapper = taxMapper;
        this.userMapper = userMapper;
        this.vehicleMapper = vehicleMapper;
        this.vehicleTypeMapper = vehicleTypeMapper;
        fillMapperForClass();
    }

    private void fillMapperForClass(){
        mapperForClass.put(Company.class,companyMapper);
        mapperForClass.put(Contract.class,contractMapper);
        mapperForClass.put(Fleet.class,fleetMapper);
        mapperForClass.put(Function.class,functionMapper);
        mapperForClass.put(InsuranceType.class,insuranceTypeMapper);
        mapperForClass.put(Invoice.class,invoiceMapper);
        mapperForClass.put(Permission.class,permissionMapper);
        mapperForClass.put(Role.class,roleMapper);
        mapperForClass.put(Tax.class,taxMapper);
        mapperForClass.put(User.class,userMapper);
        mapperForClass.put(Vehicle.class,vehicleMapper);
        mapperForClass.put(VehicleType.class,vehicleTypeMapper);
    }

    public AbstractMapper getMapperForClass(Class clazz){
        return mapperForClass.get(clazz);
    }


    public CompanyMapper getCompanyMapper() {
        return companyMapper;
    }

    public ContractMapper getContractMapper() {
        return contractMapper;
    }

    public FleetMapper getFleetMapper() {
        return fleetMapper;
    }

    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    public InsuranceTypeMapper getInsuranceTypeMapper() {
        return insuranceTypeMapper;
    }

    public InvoiceMapper getInvoiceMapper() {
        return invoiceMapper;
    }

    public PermissionMapper getPermissionMapper() {
        return permissionMapper;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public TaxMapper getTaxMapper() {
        return taxMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public VehicleMapper getVehicleMapper() {
        return vehicleMapper;
    }

    public VehicleTypeMapper getVehicleTypeMapper() {
        return vehicleTypeMapper;
    }

    public HashMap<Class, AbstractMapper> getMapperForClass() {
        return mapperForClass;
    }
}
