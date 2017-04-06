package solvas.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import solvas.authentication.ajax.AjaxLoginProcessingFilter;
import solvas.authentication.jwt.JwtAuthenticationProvider;
import solvas.authentication.jwt.JwtTokenAuthenticationProcessingFilter;
import solvas.authentication.jwt.SkipPathRequestMatcher;
import solvas.authentication.jwt.TokenExtractor;
import solvas.authentication.utils.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Configure Spring Security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/auth/login";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/token";

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationProvider ajaxAuthenticationProvider;
    private final AuthenticationProvider jwtAuthenticationProvider;
    private final TokenExtractor tokenExtractor;
    private final ObjectMapper objectMapper;

    /**
     *
     * @param ajaxAuthenticationProvider Authentication provider for AJAX username+password auth
     * @param tokenExtractor Extractor to get JWT from request
     * @param objectMapper Jackson Object Mapper
     * @param failureHandler Handler called when authentication fails
     * @param jwtAuthenticationProvider Authentication provider for JWT
     * @param successHandler Handler called when authentication succeeds
     * @param authenticationEntryPoint Entry point for authentication
     */
    @Autowired
    public WebSecurityConfig(AuthenticationProvider ajaxAuthenticationProvider, TokenExtractor tokenExtractor, ObjectMapper objectMapper, AuthenticationFailureHandler failureHandler, JwtAuthenticationProvider jwtAuthenticationProvider, AuthenticationSuccessHandler successHandler, RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.ajaxAuthenticationProvider = ajaxAuthenticationProvider;
        this.tokenExtractor = tokenExtractor;
        this.objectMapper = objectMapper;
        this.failureHandler = failureHandler;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.successHandler = successHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(
                FORM_BASED_LOGIN_ENTRY_POINT,
                successHandler,
                failureHandler,
                objectMapper);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter
            = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable() // We don't need CSRF for JWT based authentication
        .exceptionHandling()
        .authenticationEntryPoint(this.authenticationEntryPoint)

        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
            .authorizeRequests()
                .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
                .antMatchers(HttpMethod.OPTIONS).permitAll()
        .and()
            .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
        .and()
            .addFilterBefore(new CorsFilter(FORM_BASED_LOGIN_ENTRY_POINT), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
