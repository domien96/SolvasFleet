package solvas.authentication.utils;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Because the AJAX auth has no Spring Controller associated, we need a dirty hack to pass CORS headers.
 * Otherwise preflight requests 404
 */
public class CorsFilter extends OncePerRequestFilter {
    private final RequestMatcher matcher;

    /**
     * @param path Path this filter should set CORS headers for
     */
    public CorsFilter(String path) {
        matcher = new AntPathRequestMatcher(path);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (matcher.matches(request)) {
            response.setHeader("Access-Control-Allow-Origin", "*");//* or origin as u prefer
            response.setHeader("Access-Control-Allow-Credentials", "true");
            if(request.getHeader("Access-Control-Request-Headers") != null) {
                response.setHeader("Access-Control-Allow-Headers",
                        request.getHeader("Access-Control-Request-Headers")
                );
            }

            if(HttpMethod.OPTIONS.matches(request.getMethod())) {
                response.getWriter().print("OK");
                response.getWriter().flush();
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}