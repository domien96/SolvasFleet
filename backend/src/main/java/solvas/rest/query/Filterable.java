package solvas.rest.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Represents something that can be used as a filter in the persistence layer.
 *
 * It would seem this object depends on the column names of the persistence layer. This
 * is not true: the JPA criteria API uses the class- and member names. Unfortunately, since
 * we are limited to .hbm.xml mapping files, we cannot use the JPA Metamodel generator, which
 * provides a type-safe way to access fields in the classes.
 *
 * @param <T> The type of the model this filter is for.
 *
 * @author Niko Strijbol
 */
public interface Filterable<T> {

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
}