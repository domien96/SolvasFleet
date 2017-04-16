package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;

import java.util.Collection;

/**
 * Interface to find id of the company an entity belongs to
 */
@FunctionalInterface
public interface CompanyIdResolver {
    /**
     *
     * @param targetId Id of the entity we want to resolve the company id for
     * @return The id's of all companies this entity belongs to
     * @throws EntityNotFoundException Entity could not be found
     */
    Collection<Integer> resolve(int targetId) throws EntityNotFoundException;
}
