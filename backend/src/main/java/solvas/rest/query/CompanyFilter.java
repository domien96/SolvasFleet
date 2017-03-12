package solvas.rest.query;

import solvas.models.Company;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Filters for a {@link Company}.
 *
 * @author Niko Strijbol
 */
@SuppressWarnings("unused")
public class CompanyFilter implements Filterable<Company> {

    private String city;
    private String country;
    private String nameContains;
    private String postalCode;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Company> root) {
        List<Predicate> predicates = new ArrayList<>();

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
}