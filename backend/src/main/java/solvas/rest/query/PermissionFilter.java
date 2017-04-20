package solvas.rest.query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.RoleDao;
import solvas.service.models.Permission;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;

/**
 * Filter for permissions
 */
@Component
public class PermissionFilter implements Filter<Permission> {
    private Collection<Integer> ids = new HashSet<>();
    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Permission> root) {
        Collection<Predicate> predicates = new HashSet<>();

        if (ids.size() > 0) {
               Predicate[] rolePredicates = ids.stream()
                        .map(id -> builder.equal(root.get("id"), id))
                        .toArray(Predicate[]::new);

                predicates.add(builder.or(rolePredicates));
        }
        return predicates;
    }

    public void setIds(Collection<Integer> ids) {
        this.ids = ids;
    }
}
