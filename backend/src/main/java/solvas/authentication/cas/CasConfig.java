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

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties properties = new ServiceProperties();
        properties.setService("http://dev.event.fkgent.be:3000/auth/login");
        return properties;
    }

    @Bean
    @Autowired
    public CasAuthenticationEntryPoint casEntryPoint(ServiceProperties serviceProperties) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl("https://login.ugent.be/");
        entryPoint.setServiceProperties(serviceProperties);
        return entryPoint;
    }

    @Bean
    @Autowired
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties serviceProperties, ResourceLoader resourceLoader) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties);
        provider.setTicketValidator(new TicketValidator("https://login.ugent.be", resourceLoader));
        provider.setKey("my_key");

        provider.setUserDetailsService(new SolvasUserDetailsService());
        return provider;

    }
}
