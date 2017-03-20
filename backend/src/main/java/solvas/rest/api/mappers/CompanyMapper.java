package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

/**
 * Mapper between Company and ApiCompany
 */
@Component
public class CompanyMapper extends AbstractMapper<Company,ApiCompany> {

    private String rootPath="/companies/";

    /**
     * Create a mapper between Company and ApiCompany
     *
     * @param daoContext The context for Dao's
     */
    public CompanyMapper(DaoContext daoContext) {
        super(daoContext, "name", "vatNumber", "phoneNumber");
    }

    @Override
    public Company convertToModel(ApiCompany apiCompany) throws FieldNotFoundException {
        Company company = new Company();
        company.setId(apiCompany.getId());


        if (company.getId()!=0) {
            //update
            company = daoContext.getCompanyDao().find(company.getId());
        }

        copySharedAttributes(company, apiCompany);

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

        apiCompany.setUrl(rootPath+apiCompany.getId());
        return apiCompany;
    }


}
