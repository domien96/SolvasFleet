package solvas.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configure the password encoder
 */
@Configuration
public class PasswordConfig {

    /**
     * Bean for BCrypt
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder BCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for BCrypt
     * @return PasswordEncoder
     */
    @Bean
    @Primary
    @Profile({"debug", "test"})
    public PasswordEncoder NoOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * @param passwordEncoder Encoder to avoid storing plaintext
     * @param userDetailsService Service to resolve username to UserDetails instance
     * @return The authentication provider
     */
    @Bean
    @Autowired
    public AuthenticationProvider ajaxAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                         UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
