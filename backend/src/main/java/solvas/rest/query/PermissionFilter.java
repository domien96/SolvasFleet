package solvas.rest.query;


import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.RoleDao;
import solvas.service.models.Permission;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;

public class PermissionFilter implements Filter<Permission> {
    private final RoleDao roleDao;
    private int roleId;

    public PermissionFilter(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Permission> root) {
        Collection<Predicate> predicates = new HashSet<>();

        if (roleId >= 0) {
            try {
               Predicate[] rolePredicates = roleDao.find(roleId).getPermissions().stream()
                        .map(Permission::getId)
                        .map(id -> builder.equal(root.get("id"), id))
                        .toArray(Predicate[]::new);

                predicates.add(builder.or(rolePredicates));
            } catch (EntityNotFoundException ignored) {
                // TODO: what to do when filter is invalid?
            }
        }
        return predicates;
    }

    public void setRole(int roleId) {
        this.roleId = roleId;
    }
}
