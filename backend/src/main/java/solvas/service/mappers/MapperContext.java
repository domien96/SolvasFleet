package solvas.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.service.mappers.*;
import solvas.service.models.*;

import java.util.HashMap;

/**
 * A single point of access to all mappers
 *  allows search by class
 *  mappers are autowired by spring
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

    /**
     * Helper function to fill the hash map with correct data
     */
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

    /**
     * Get CompanyMapper for this context
     * @return A CompanyMapper
     */
    public CompanyMapper getCompanyMapper() {
        return companyMapper;
    }

    /**
     * Get ContractMapper for this context
     * @return A ContractMapper
     */
    public ContractMapper getContractMapper() {
        return contractMapper;
    }

    /**
     * Get FleetMapper for this context
     * @return A FleetMapper
     */
    public FleetMapper getFleetMapper() {
        return fleetMapper;
    }

    /**
     * Get FunctionMapper for this context
     * @return A FunctionMapper
     */
    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    /**
     * Get InsuranceTypeMapper for this context
     * @return A InsuranceTypeMapper
     */
    public InsuranceTypeMapper getInsuranceTypeMapper() {
        return insuranceTypeMapper;
    }

    /**
     * Get InvoiceMapper for this context
     * @return A InvoiceMapper
     */
    public InvoiceMapper getInvoiceMapper() {
        return invoiceMapper;
    }

    /**
     * Get PermissionMapper for this context
     * @return A PermissionMapper
     */
    public PermissionMapper getPermissionMapper() {
        return permissionMapper;
    }

    /**
     * Get RoleMapper for this context
     * @return A RoleMapper
     */
    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    /**
     * Get TaxMapper for this context
     * @return A TaxMapper
     */
    public TaxMapper getTaxMapper() {
        return taxMapper;
    }

    /**
     * Get UserMapper for this context
     * @return A UserMapper
     */
    public UserMapper getUserMapper() {
        return userMapper;
    }

    /**
     * Get VehicleMapper for this context
     * @return A VehicleMapper
     */
    public VehicleMapper getVehicleMapper() {
        return vehicleMapper;
    }

    /**
     * Get VehicleTypeMapper for this context
     * @return A VehicleTypeMapper
     */
    public VehicleTypeMapper getVehicleTypeMapper() {
        return vehicleTypeMapper;
    }


}
