package solvas.rest.query;

import solvas.persistence.api.Filter;
import solvas.service.models.EntityType;
import solvas.service.models.Revision;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;

/**
 * Filters for a {@link solvas.service.models.Revision}.
 *
 * @author sjabasti
 */
public class AuditFilter implements Filter<Revision> {

    private int user = -1;
    private String entityType;
    private int entity = -1;
    private String method;
    private String before;
    private String after;

    @Override
    public Collection<Predicate> asPredicates(CriteriaBuilder builder, Root<Revision> root) {
        Collection<Predicate> predicates =  new HashSet<>();
        if (user >= 0) {
            predicates.add(builder.equal(root.get("user"), user));
        }
        if (entity >= 0) {
            predicates.add(builder.equal(root.get("entity"), entity));
        }
        if (entityType!=null) {
            predicates.add(builder.equal(
                    root.get("entityType"),
                    entityType
            ));
        }
        if (before!=null) {
            LocalDateTime beforeDate = LocalDateTime.parse(before);
            predicates.add(builder.lessThanOrEqualTo(
                    root.get("logDate"),
                    beforeDate
            ));
        }
        if (after!=null) {
            LocalDateTime afterDate = LocalDateTime.parse(after);
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get("logDate"),
                    afterDate
            ));
        }

        if (method!=null) {
            try {
                predicates.add(builder.equal(
                        root.get("method"),
                        EntityType.toClass(method)
                ));
            } catch (ClassNotFoundException e) {
                predicates.add(builder.equal(
                        root.get("method"),
                        null // Return nothing, method does not exist
                ));
            }
        }
        return predicates;
    }

    /**
     * Set user to filter revisions for
     * @param user id of the user
     */
    public void setUser(int user) {
        this.user = user;
    }

    /**
     * Set entity type to filter revisions for
     * @param entityType type of the entity
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * Set entity to filter revisions for
     * @param entity entity id
     */
    public void setEntity(int entity) {
        this.entity = entity;
    }

    /**
     * Set method to filter revisions for
     * @param method type of method
     */
    public void setMethod(String method) {
        this.method = method;
    }


    /**
     * return revisions before a date
     * @param before date
     */
    public void setBefore(String before) {
        this.before = before;
    }


    /**
     * return revisions after a date
     * @param after date
     */
    public void setAfter(String after) {
        this.after = after;
    }


}