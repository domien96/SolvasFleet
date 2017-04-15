package solvas.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authorization.resolvers.CompanyIdResolver;
import solvas.authorization.resolvers.CompanyToCompanyIdResolver;
import solvas.authorization.resolvers.FleetToCompanyIdResolver;
import solvas.persistence.api.DaoContext;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResolverContext {
    private final Map<String, CompanyIdResolver> resolvers = new HashMap<>();

    @Autowired
    public ResolverContext(DaoContext daoContext) {
        resolvers.put("fleet", new FleetToCompanyIdResolver(daoContext.getFleetDao()));
        resolvers.put("company", new CompanyToCompanyIdResolver());
    }

    public CompanyIdResolver getResolver(String resource) {
        return resolvers.get(resource);
    }
}
