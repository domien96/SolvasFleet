package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.persistence.DaoContext;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class CompanyMapper extends AbstractMapper<Company,ApiCompany> {

    private String rootPath="/companies/";

    /**
     * TODO document
     *
     * @param daoContext
     */
    public CompanyMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Company convertToModel(ApiCompany apiCompany) {
        Company company = new Company();
        company.setId(apiCompany.getId());


        if (company.getId()!=0) {
            //update
            company = daoContext.getCompanyDao().find(company.getId());
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
        apiCompany.setUrl(rootPath+apiCompany.getId());
        return apiCompany;
    }
}
