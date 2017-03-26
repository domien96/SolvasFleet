package solvas.authentication.cas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import solvas.authentication.cas.userdetails.SolvasUserDetailsService;

@Configuration
public class CasConfig {

    // TODO put these in properties file
    private final String SERVICE_URL = "http://dev.event.fkgent.be:3000/auth/login";
    private final String CAS_SERVER_URL = "https://login.ugent.be/";
    private final String CAS_KEY = "my_key"; // No idea what this does

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties properties = new ServiceProperties();
        properties.setService(SERVICE_URL);
        return properties;
    }

    @Bean
    @Autowired
    public CasAuthenticationEntryPoint casEntryPoint(ServiceProperties serviceProperties) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl(CAS_SERVER_URL);
        entryPoint.setServiceProperties(serviceProperties);
        return entryPoint;
    }

    @Bean
    @Autowired
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties serviceProperties, ResourceLoader resourceLoader) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties);
        provider.setTicketValidator(new TicketValidator(CAS_SERVER_URL, resourceLoader));
        provider.setKey(CAS_KEY);

        provider.setUserDetailsService(new SolvasUserDetailsService());
        return provider;

    }
}
