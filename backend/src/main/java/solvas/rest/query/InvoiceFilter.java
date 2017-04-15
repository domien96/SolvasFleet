package solvas.rest.query;

import solvas.service.models.Model;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by steve on 15/04/2017.
 */
public class InvoiceFilter extends ArchiveFilter<Invoice> {

    private int fleet = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Model> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);
        if (fleet >= 0) {
            predicates.add(builder.equal(root.get("fleet"), fleet));
        }
        return predicates;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }
}

