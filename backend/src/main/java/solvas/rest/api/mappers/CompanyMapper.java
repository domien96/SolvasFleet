package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class CompanyMapper extends AbstractMapper<Company,ApiCompany> {

    /**
     * TODO document
     * @param roleDao
     * @param companyDao
     * @param userDao
     * @param vehicleDao
     * @param vehicleTypeDao
     * @param fleetSubscriptionDao
     * @param fleetDao
     * @param subFleetDao
     */
    public CompanyMapper(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao
            , VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao, FleetDao fleetDao, SubFleetDao subFleetDao) {
        super(roleDao, companyDao, userDao, vehicleDao, vehicleTypeDao, fleetSubscriptionDao, fleetDao, subFleetDao);
    }

    @Override
    public Company convertToModel(ApiCompany apiCompany) {
        Company company = new Company();
        company.setId(apiCompany.getId());


        if (company.getId()!=0) {
            //update
            company = companyDao.find(company.getId());
            if (company==null){
                company=new Company();
            }
        }

        company.setName(apiCompany.getName()==null
                ? company.getName() : apiCompany.getName());
        company.setVatNumber(apiCompany.getVatNumber()==null
                ? company.getVatNumber() : apiCompany.getVatNumber());
        company.setPhoneNumber(apiCompany.getPhoneNumber()==null
                ? company.getPhoneNumber() : apiCompany.getPhoneNumber());
        if (apiCompany.getAddress()!=null) {
            company.setAddressCity(apiCompany.getAddress().getCity());
            company.setAddressCountry(apiCompany.getAddress().getCountry());
            company.setAddressHouseNumber(apiCompany.getAddress().getHouseNumber());
            company.setAddressPostalCode(apiCompany.getAddress().getPostalCode());
            company.setAddressStreet(apiCompany.getAddress().getStreet());
        } else {
            company.setAddressStreet(company.getAddressStreet());
            company.setAddressCity(company.getAddressCity());
            company.setAddressHouseNumber(company.getAddressHouseNumber());
            company.setAddressPostalCode(company.getAddressPostalCode());
            company.setAddressCountry(company.getAddressCountry());
        }
        company.setUpdatedAt(null);
        company.setCreatedAt(company.getCreatedAt());
        return company;
    }

    @Override
    public ApiCompany convertToApiModel(Company company) {
        ApiCompany apiCompany = new ApiCompany();
        apiCompany.setId(company.getId());
        apiCompany.setName(company.getName());
        apiCompany.setVatNumber(company.getVatNumber());
        apiCompany.setPhoneNumber(company.getPhoneNumber());
        apiCompany.setAddress(new ApiAddress());
        apiCompany.getAddress().setCity(company.getAddressCity());
        apiCompany.getAddress().setCountry(company.getAddressCountry());
        apiCompany.getAddress().setHouseNumber(company.getAddressHouseNumber());
        apiCompany.getAddress().setPostalCode(company.getAddressPostalCode());
        apiCompany.getAddress().setStreet(company.getAddressStreet());
        apiCompany.setCreatedAt(company.getCreatedAt());
        apiCompany.setUpdatedAt(company.getUpdatedAt());
        apiCompany.setUrl(company.getUrl());
        return apiCompany;
    }
}
