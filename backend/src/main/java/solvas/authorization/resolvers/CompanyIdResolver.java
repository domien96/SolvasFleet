package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;

import java.util.Collection;

@FunctionalInterface
public interface CompanyIdResolver {
    Collection<Integer> resolve(int targetId) throws EntityNotFoundException;
}
