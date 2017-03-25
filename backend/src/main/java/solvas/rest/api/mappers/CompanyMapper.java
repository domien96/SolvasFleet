package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import solvas.models.Company;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

/**
 * Mapper between Company and ApiCompany
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
        super(daoContext);
    }

    @Override
    public Company convertToModel(ApiCompany apiCompany) throws EntityNotFoundException {
        Company company = new Company();
        company.setId(apiCompany.getId());


        if (company.getId()!=0) {
            //update
            company = daoContext.getCompanyDao().find(company.getId());
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
        apiCompany.setUrl(SimpleUrlBuilder.buildUrl(ROOTPATH + "{id}", company.getId()));
        return apiCompany;
    }
}
