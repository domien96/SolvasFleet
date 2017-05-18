package solvas.rest.query;

import solvas.service.models.Company;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Niko Strijbol
 */
public class CompanyInListFilter extends CompanyFilter {

    private Collection<Integer> ids;

    public void setIds(Collection<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Company> root) {
        Collection<Predicate> predicates = super.asPredicates(builder, root);

        if (ids != null) {
            if (ids.isEmpty()) {
                return Collections.singleton(builder.isTrue(builder.literal(false)));
            } else {
                predicates.add(root.get("id").in(ids));
            }
        }

        return predicates;
    }
}