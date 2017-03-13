package solvas.rest.query;

import solvas.models.Fleet;
import solvas.persistence.api.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Niko Strijbol
 */
public class FleetFilter implements Filter<Fleet> {

    private int company = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Fleet> root) {
        if (company >= 0) {
            return Collections.singleton(builder.equal(root.get("company"), company));
        }
        return Collections.emptyList();
    }

    public void setCompany(int company) {
        this.company = company;
    }
}