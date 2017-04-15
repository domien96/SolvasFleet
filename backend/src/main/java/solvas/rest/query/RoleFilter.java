package solvas.rest.query;

import solvas.service.models.Role;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * @author Niko Strijbol
 */
public class RoleFilter extends ArchiveFilter<Role> {

    private int company = -1;
    private int user = -1;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Role> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);

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