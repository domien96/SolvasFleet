package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class NoCompanyResolver implements CompanyIdResolver {
    @Override
    public Collection<Integer> resolve(int targetId) throws EntityNotFoundException {
        return new ArrayList<>();
    }
}
