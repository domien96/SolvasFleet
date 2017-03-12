package solvas.rest.query;

import solvas.models.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Filter for {@link User} objects.
 *
 * @author Niko Strijbol
 */
public class UserFilter implements Filterable<User> {

    private String email;
    private String firstName;
    private String lastName;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<User> root) {
        List<Predicate> predicates = new ArrayList<>();
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