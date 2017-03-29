package solvas.persistence.api;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Represents something that can be used as a filter in the persistence layer.
 *
 * @param <T> The type of the model this filter is for.
 *
 * @author Niko Strijbol
 */
public interface Filter<T> extends Specification<T> {

    /**
     * Convert the object to a list of {@link Predicate}s.
     *
     * From the JPA criteria documentation: if you select a field that does not exist
     * from the root, a {@link IllegalArgumentException} will be thrown. In most cases,
     * you should prevent this from happening, by not blindly mapping query parameters
     * to the root.
     *
     * @param builder The JPA builder to use.
     * @param root The JPA root, useful for selecting model fields.
     *
     * @return The predicates.
     */
    Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<T> root);

    /**
     * {@inheritDoc}
     *
     * The default implementation combines the predicates from {@link Predicate} with AND.
     */
    @Override
    default Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return Filter.this.asPredicates(cb, root)
                .stream()
                .reduce(cb::and)
                .orElseGet(() -> cb.isTrue(cb.literal(true)));
    }
}