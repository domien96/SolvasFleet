package solvas.rest.query;

import solvas.persistence.api.Filter;
import solvas.service.models.Model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Filters for a {@link Model}.
 *
 * @param <T> The model on which filtering will happen.
 */
public abstract class ArchiveFilter<T extends Model> implements Filter<T> {

    private boolean archived = false;

    /**
     * {@inheritDoc}
     *
     * The {@code archived} attribute will automatically be added.
     */
    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("archived"), archived));
        return predicates;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}