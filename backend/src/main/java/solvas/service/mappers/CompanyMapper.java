package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.service.models.Company;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

/**
 * Class to map {@link Company} in the persistence layer to {@link ApiCompany} in the API layer
 */
@Component
public class CompanyMapper extends AbstractMapper<Company,ApiCompany> {

    private static final String ROOTPATH ="/companies/";

    /**
     * Create a mapper between Company and ApiCompany
     *
     * @param daoContext The context for Dao's
     */
    public CompanyMapper(DaoContext daoContext) {
        super(daoContext, "name", "vatNumber", "phoneNumber");
    }

    @Override
    public Company convertToModel(ApiCompany apiCompany) throws FieldNotFoundException, EntityNotFoundException {
        Company company = apiCompany.getId()==0? new Company():daoContext.getCompanyDao().find(apiCompany.getId());

        copySharedAttributes(company, apiCompany);

        company.setAddressCity(apiCompany.getAddress().getCity());
        company.setAddressCountry(apiCompany.getAddress().getCountry());
        company.setAddressHouseNumber(apiCompany.getAddress().getHouseNumber());
        company.setAddressPostalCode(apiCompany.getAddress().getPostalCode());
        company.setAddressStreet(apiCompany.getAddress().getStreet());
        return company;
    }

    @Override
    public ApiCompany convertToApiModel(Company company) throws FieldNotFoundException {
        ApiCompany apiCompany = new ApiCompany();

        copyAttributes(apiCompany, company, "id", "createdAt", "updatedAt");
        copySharedAttributes(apiCompany, company);

        apiCompany.setAddress(new ApiAddress());
        apiCompany.getAddress().setCity(company.getAddressCity());
        apiCompany.getAddress().setCountry(company.getAddressCountry());
        apiCompany.getAddress().setHouseNumber(company.getAddressHouseNumber());
        apiCompany.getAddress().setPostalCode(company.getAddressPostalCode());
        apiCompany.getAddress().setStreet(company.getAddressStreet());
        apiCompany.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", company.getId()));
        return apiCompany;
    }


}
