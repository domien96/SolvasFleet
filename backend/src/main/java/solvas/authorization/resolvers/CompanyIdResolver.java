package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;

import java.util.Collection;

@FunctionalInterface
public interface CompanyIdResolver {
    Collection<Integer> resolve(int targetId) throws EntityNotFoundException;
    default Collection<Integer> resolve(ApiModel api) throws EntityNotFoundException {
        return resolve(api.getId());
    }
}
