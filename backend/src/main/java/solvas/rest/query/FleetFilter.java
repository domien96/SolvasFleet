package solvas.rest.query;

import solvas.models.Company;
import solvas.models.Fleet;
import solvas.persistence.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Niko Strijbol
 */
public class FleetFilter implements Filter<Fleet> {

    private int company;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Fleet> root) {
        if (company>=1) {
            return Collections.singleton(builder.equal(root.get("company"), company));
        }
        return new ArrayList<>();
    }

    public void setCompany(int company) {
        this.company = company;
    }
}