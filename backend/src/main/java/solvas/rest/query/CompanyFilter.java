package solvas.rest.query;

import solvas.service.models.Company;
import solvas.service.models.CompanyType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Filters for a {@link Company}.
 *
 * @author Niko Strijbol
 */
public class CompanyFilter extends ArchiveFilter<Company> {

    private String city;
    private String country;
    private String nameContains;
    private String postalCode;
    private String type;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Company> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);

        if (city != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("addressCity")),
                    city.toLowerCase()
            ));
        }
        if (country != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("addressCountry")),
                    country.toLowerCase()
            ));
        }
        if (nameContains != null) {
            predicates.add(builder.like(
                    builder.lower(root.get("name")),
                    "%" + nameContains.toLowerCase() + "%"
            ));
        }
        if (postalCode != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("addressPostalCode")),
                    postalCode.toLowerCase()
            ));
        }

        if (type != null) {
            // We don't need to check if the type actually exists. If it doesn't exist, CompanyType.fromString(type)
            // will return null, which in turn will not match with any company and thus return no companies.
            // This behaviour is allowed by the API.
            predicates.add(builder.equal(
                    root.get("type"),
                    CompanyType.fromString(type)
            ));
        }

        return predicates;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNameContains(String nameContains) {
        this.nameContains = nameContains;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setType(String type) {
        this.type = type;
    }
}