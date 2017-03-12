package solvas.rest.api.mappings;

import solvas.models.Company;
import solvas.rest.api.models.ApiAddress;
import solvas.rest.api.models.ApiCompany;

/**
 * Created by steve on 11/03/2017.
 */
public class CompanyMapping extends Mapping<Company,ApiCompany> {


    @Override
    public Company convertToModel(ApiCompany apiCompany) {
        Company company = new Company();
        company.setId(apiCompany.getId());
        company.setName(apiCompany.getName());
        company.setVatNumber(apiCompany.getVatNumber());
        company.setPhoneNumber(apiCompany.getPhoneNumber());
        company.setAddressCity(apiCompany.getAddress().getCity());
        company.setAddressCountry(apiCompany.getAddress().getCountry());
        company.setAddressHouseNumber(apiCompany.getAddress().getHouseNumber());
        company.setAddressPostalCode(apiCompany.getAddress().getPostalCode());
        company.setAddressStreet(apiCompany.getAddress().getStreet());
        company.setUpdatedAt(apiCompany.getUpdatedAt());
        company.setCreatedAt(apiCompany.getCreatedAt());
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
