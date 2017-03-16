package solvas.rest.query;

import solvas.models.Role;
import solvas.persistence.api.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class RoleFilter implements Filter<Role> {

    private int company = -1;
    private int user = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Role> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (company >= 0) {
            predicates.add(builder.equal(root.get("company"), company));
        }
        if (user >= 0) {
            predicates.add(builder.equal(root.get("user"), user));
        }

        return predicates;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public void setUser(int user) {
        this.user = user;
    }
}