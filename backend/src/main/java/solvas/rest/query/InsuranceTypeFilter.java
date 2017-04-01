package solvas.rest.query;

import solvas.persistence.api.Filter;
import solvas.service.models.InsuranceType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by domien on 1/04/2017.
 */
public class InsuranceTypeFilter extends ArchiveFilter<InsuranceType> {
    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<InsuranceType> root) {
        return super.asPredicates(builder,root);
    }
}
