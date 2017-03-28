package solvas.rest.query;

import solvas.models.Company;
import solvas.models.Model;
import solvas.persistence.api.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Filters for a {@link Model}.
 *
 */
@SuppressWarnings("unused")
public abstract class ArchiveFilter<T extends Model> implements Filter<T> {

    private boolean archived=false;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(
                root.get("archived"),archived
        ));
        return predicates;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}