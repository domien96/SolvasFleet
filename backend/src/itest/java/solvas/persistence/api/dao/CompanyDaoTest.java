package solvas.persistence.api.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solvas.service.mappers.exceptions.FieldNotFoundException;
import solvas.service.models.Company;

import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration tests for custom behaviour on the company DAO.
 *
 * @author Niko Strijbol
 * @author Karel Vandenbussche
 */
public class CompanyDaoTest extends DaoTest {

    @Autowired
    private CompanyDao companyDao;


    /**
     * Test finding companies with a certain name.
     */
    @Test
    public void withName() {

        Company company = manager.find(Company.class, 1);
        manager.detach(company);

        // Test existing
        assertThat(companyDao.findByName(company.getName()), hasSize(1));
        // Save and test again.
        Company newCompany = new Company();
        newCompany.setVatNumber("TestVAT");
        copyAttributes(newCompany,company,"name", "phoneNumber","addressCity","addressCountry","addressHouseNumber",
                "addressPostalCode","addressStreet","type");

        companyDao.save(newCompany);
        assertThat(companyDao.findByName(company.getName()), hasSize(2));
    }

    /**
     * Copy attribute from source (if set), to target
     *
     * @param target     Entity to copy to
     * @param src        Entity to copy from
     * @param attributes attributes to copy
     * @throws FieldNotFoundException If a field wasn't found or was inaccessible
     */

    private void copyAttributes(Object target, Object src, String... attributes) {
        for (String attribute : attributes) {
            copyNotNull(target, src, attribute);
        }
    }


    /**
     * Searches a field of a class, if it is not found, it will search in its superclasses
     * @param clazz base class of which the field must be searched
     * @param name attribute to copy
     * @return Field with name
     * @throws FieldNotFoundException If field wasn't found
     */
    private Field findFieldInSuper(Class<?> clazz, String name) {
        Class<?> current = clazz;
        do {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {}
        } while((current = current.getSuperclass()) != null);
        throw new FieldNotFoundException("Field "+name+"not found");
    }


    /**
     * Copy attribute from source (if set), to target
     *
     * @param target Entity to copy to
     * @param source Entity to copy from
     * @param name   attribute to copy
     * @throws FieldNotFoundException If field wasn't found or was inaccessible
     */
    private void copyNotNull(Object target, Object source, String name) {
        Field targetField = findFieldInSuper(target.getClass(),name);
        Field sourceField = findFieldInSuper(source.getClass(),name);
        try {
            targetField.setAccessible(true);
            sourceField.setAccessible(true);
            if (sourceField.get(source) != null) {
                targetField.set(target, sourceField.get(source));
            }
        } catch (IllegalAccessException e) {
            throw new FieldNotFoundException(e);
        } finally {
            targetField.setAccessible(false);
            sourceField.setAccessible(false);
        }
    }

}