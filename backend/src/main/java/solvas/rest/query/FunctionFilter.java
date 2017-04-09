package solvas.rest.query;


import solvas.service.models.Function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class FunctionFilter extends ArchiveFilter<Function> {

    private int company = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Function> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);
        if (company >= 0) {
            predicates.add(builder.equal(root.get("user"), company));
        }
        return predicates;
    }

    public void setUser(int userId) {
        this.company = userId;
    }
}
