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
public class FleetFilter extends ArchiveFilter<Fleet> {

    private int company = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Fleet> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);
        if (company >= 0) {
            predicates.add(builder.equal(root.get("company"), company));
        }
        return predicates;
    }

    public void setCompany(int company) {
        this.company = company;
    }
}