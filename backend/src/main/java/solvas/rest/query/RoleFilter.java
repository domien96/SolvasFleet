package solvas.rest.query;

import solvas.persistence.api.Filter;
import solvas.service.models.Role;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Filter for {@link Role}s
 */
public class RoleFilter implements Filter<Role> {

    private int user = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Role> root) {
        Collection<Predicate> predicates = new ArrayList<>();

        if (user >= 0) {
            predicates.add(builder.equal(root.get("user"), user));
        }

        return predicates;
    }

    public void setUser(int user) {
        this.user = user;
    }
}