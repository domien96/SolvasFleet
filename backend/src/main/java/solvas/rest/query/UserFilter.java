package solvas.rest.query;

import solvas.service.models.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Filter for {@link User} objects.
 *
 * @author Niko Strijbol
 */
public class UserFilter extends ArchiveFilter<User> {

    private String email;
    private String firstName;
    private String lastName;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<User> root) {
        Collection<Predicate> predicates = super.asPredicates(builder,root);
        if (email != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("email")),
                    email.toLowerCase()
            ));
        }
        if (firstName != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("firstName")),
                    firstName.toLowerCase()
            ));
        }
        if (lastName != null) {
            predicates.add(builder.equal(
                    builder.lower(root.get("lastName")),
                    lastName.toLowerCase()
            ));
        }
        return predicates;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}