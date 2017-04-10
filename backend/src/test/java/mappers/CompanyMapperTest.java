package mappers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import solvas.service.models.Company;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.service.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Tests to check correct mapping of a company
 */
public class CompanyMapperTest {
    @Mock
    private DaoContext context;
    @Mock
    private CompanyDao companyDaoMock;
    private CompanyMapper mapper;

    /**
     * Setting up the tests of companyMapper
     */
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(context.getCompanyDao()).thenReturn(companyDaoMock);
        mapper=new CompanyMapper(context);
    }

    /**
     * Test the conversion ApiCompany->Company
     */
    @Test
    public void convertToCompany() throws EntityNotFoundException {
        ApiCompany apiCompany = random(ApiCompany.class);
        Company random = random(Company.class);
        random.setId(apiCompany.getId());
        when(companyDaoMock.find(anyInt())).thenReturn(random);

        Company converted = mapper.convertToModel(apiCompany);
        assertThat(converted.getId(),is(apiCompany.getId()));
        assertThat(converted.getName(),is(apiCompany.getName()));
        assertThat(converted.getAddressHouseNumber(),is(apiCompany.getAddress().getHouseNumber()));
        assertThat(converted.getAddressCountry(),is(apiCompany.getAddress().getCountry()));
        assertThat(converted.getAddressCity(),is(apiCompany.getAddress().getCity()));
        assertThat(converted.getAddressPostalCode(),is(apiCompany.getAddress().getPostalCode()));
        assertThat(converted.getAddressStreet(),is(apiCompany.getAddress().getStreet()));
        assertThat(converted.getPhoneNumber(),is(apiCompany.getPhoneNumber()));
        assertThat(converted.getVatNumber(),is(apiCompany.getVatNumber()));
    }

    /**
     * Test the conversion Company->ApiCompany
     */
    @Test
    public void convertToApiCompany()
    {
        Company company = random(Company.class);
        ApiCompany converted = mapper.convertToApiModel(company);
        assertThat(converted.getId(),is(company.getId()));
        assertThat(converted.getName(),is(company.getName()));
        assertThat(converted.getAddress().getHouseNumber(),is(company.getAddressHouseNumber()));
        assertThat(converted.getAddress().getCity(),is(company.getAddressCity()));
        assertThat(converted.getAddress().getCountry(),is(company.getAddressCountry()));
        assertThat(converted.getAddress().getPostalCode(),is(company.getAddressPostalCode()));
        assertThat(converted.getAddress().getStreet(),is(company.getAddressStreet()));
        assertThat(converted.getPhoneNumber(),is(company.getPhoneNumber()));
        assertThat(converted.getVatNumber(),is(company.getVatNumber()));
        assertThat(converted.getUpdatedAt(),is(company.getUpdatedAt()));
        assertThat(converted.getCreatedAt(),is(company.getCreatedAt()));
    }
}
