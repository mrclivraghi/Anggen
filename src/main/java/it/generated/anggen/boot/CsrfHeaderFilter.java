
package it.generated.anggen.boot;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@Order(2147483640)
public class CsrfHeaderFilter
    extends OncePerRequestFilter
{


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException
    {
        org.springframework.security.web.csrf.CsrfToken csrf = (org.springframework.security.web.csrf.CsrfToken) request.getAttribute(org.springframework.security.web.csrf.CsrfToken.class.getName());
        if (csrf != null) {
        javax.servlet.http.Cookie cookie = org.springframework.web.util.WebUtils.getCookie(request, "XSRF-TOKEN");
        String token = csrf.getToken();
        if (cookie == null || token != null	&& !token.equals(cookie.getValue())) {
        cookie = new javax.servlet.http.Cookie("XSRF-TOKEN", token);
        cookie.setPath("/ServerTestApp");
        response.addCookie(cookie);
        }
        }
        filterChain.doFilter(request, response);
    }

}
