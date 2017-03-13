package solvas.rest.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.persistence.DaoContext;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class CompanyMapper extends AbstractMapper<Company,ApiCompany> {

    private final DaoContext daoContext;
    /**
     * TODO document
     *
     * @param daoContext
     */
    public CompanyMapper(@Autowired DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    @Override
    public Company convertToModel(ApiCompany apiCompany) throws EntityNotFoundException, FieldNotFoundException {
        Company company = new Company();
        company.setId(apiCompany.getId());

        if (company.getId()!=0) {
            //update
            company = daoContext.getCompanyDao().find(company.getId());
        }

        copyAttributes(company, apiCompany, "phoneNumber", "vatNumber", "name");
        if (apiCompany.getAddress()!=null) {
            company.setAddressCity(apiCompany.getAddress().getCity());
            company.setAddressCountry(apiCompany.getAddress().getCountry());
            company.setAddressHouseNumber(apiCompany.getAddress().getHouseNumber());
            company.setAddressPostalCode(apiCompany.getAddress().getPostalCode());
            company.setAddressStreet(apiCompany.getAddress().getStreet());
        }

        company.setUpdatedAt(null);
        company.setCreatedAt(company.getCreatedAt());
        return company;
    }

    @Override
    public ApiCompany convertToApiModel(Company company) throws FieldNotFoundException {
        ApiCompany apiCompany = new ApiCompany();

        copyAttributes(apiCompany, company, "id", "vatNumber", "phoneNumber", "createdAt", "updatedAt");

        apiCompany.setAddress(new ApiAddress());
        apiCompany.getAddress().setCity(company.getAddressCity());
        apiCompany.getAddress().setCountry(company.getAddressCountry());
        apiCompany.getAddress().setHouseNumber(company.getAddressHouseNumber());
        apiCompany.getAddress().setPostalCode(company.getAddressPostalCode());

        apiCompany.setUrl(company.getUrl()); // TODO: decouple url from domainlayer
        return apiCompany;
    }
}
