package solvas.persistence.api;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Represents something that can be used as a filter in the persistence layer.
 *
 * @param <T> The type of the model this filter is for.
 *
 * @author Niko Strijbol
 */
public interface Filter<T> {

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
     * Convert multiple predicates to a Filter.
     *
     * @param predicates The predicates.
     *
     * @param <R> The return type.
     *
     * @return The filterable.
     */
    @SafeVarargs
    static <R> Filter<R> predicates(BiFunction<CriteriaBuilder, Root<R>, Predicate>... predicates) {
        return (builder, root) -> Arrays.stream(predicates)
                .map(function -> function.apply(builder, root))
                .collect(Collectors.toList());
    }

    /**
     * Convert a single predicate to a filterable.
     *
     * @param predicate The predicate.
     *
     * @param <R> The return type.
     *
     * @return The filterable.
     */
    static <R> Filter<R> predicate(BiFunction<CriteriaBuilder, Root<R>, Predicate> predicate) {
        return (builder, root) -> Collections.singleton(predicate.apply(builder, root));
    }

    /**
     * Add another predicate to this filter. They will be added by AND.
     *
     * @param predicate The predicate to add.
     *
     * @return A new filterable.
     */
    default Filter<T> and(BiFunction<CriteriaBuilder, Root<T>, Predicate> predicate) {
        return (builder, root) -> {
            List<Predicate> existing = new ArrayList<>(Filter.this.asPredicates(builder, root));
            existing.add(predicate.apply(builder, root));
            return existing;
        };
    }
}