package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Resolver used when an entity doesn't depend on a company (returns empty list for ids)
 */
public class NoCompanyResolver implements CompanyIdResolver {
    @Override
    public Collection<Integer> resolve(int targetId) throws EntityNotFoundException {
        return new ArrayList<>();
    }
}
