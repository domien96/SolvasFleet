package solvas.authentication.cas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import solvas.authentication.user.SolvasUserDetailsService;

/**
 * Configure CAS properties
 */
@Configuration
@ConfigurationProperties(prefix = "security.cas")
@PropertySource(value = {"security.properties"})
public class CasConfig {

    // TODO put these in properties file
    private String serviceUrl;
    private String casServerUrl;
    private String casKey; // No idea what this does

    /**
     * Configure the CAS-login url which receives the CAS-token
     * @return serviceProperties
     */
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties properties = new ServiceProperties();
        properties.setService(serviceUrl);
        return properties;
    }

    /**
     * Configure the entryPoint for the cas service
     * @param serviceProperties The serviceProperties for this CAS service
     * @return EntryPoint for cas authentication
     */
    @Bean
    @Autowired
    public CasAuthenticationEntryPoint casEntryPoint(ServiceProperties serviceProperties) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl(casServerUrl);
        entryPoint.setServiceProperties(serviceProperties);
        return entryPoint;
    }

    /**
     * Configure the authentication provider for this CAS service
     * @param serviceProperties The serviceProperties for this CAS service
     * @param resourceLoader To load resources
     * @return The authentication provider
     */
    @Bean
    @Autowired
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties serviceProperties, ResourceLoader resourceLoader) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties);
        provider.setTicketValidator(new TicketValidator(casServerUrl, resourceLoader));
        provider.setKey(casKey);

        provider.setUserDetailsService(new SolvasUserDetailsService());
        return provider;

    }
}
