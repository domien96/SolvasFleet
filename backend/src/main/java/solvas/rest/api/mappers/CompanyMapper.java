package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.persistence.api.DaoContext;
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
        super(daoContext);
    }

    @Override
    public Company convertToModel(ApiCompany apiCompany, Company company) {
        company.setName(apiCompany.getName());
        company.setVatNumber(apiCompany.getVatNumber());
        company.setPhoneNumber(apiCompany.getPhoneNumber());
        company.setAddressCity(apiCompany.getAddress().getCity());
        company.setAddressCountry(apiCompany.getAddress().getCountry());
        company.setAddressHouseNumber(apiCompany.getAddress().getHouseNumber());
        company.setAddressPostalCode(apiCompany.getAddress().getPostalCode());
        company.setAddressStreet(apiCompany.getAddress().getStreet());
        return company;
    }

    @Override
    public Company convertToEmptyModel(ApiCompany api) throws DependantEntityNotFound {
        return convertToModel(api,new Company());
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
        apiCompany.setUrl(rootPath+apiCompany.getId());
        return apiCompany;
    }
}
