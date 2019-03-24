package alexa.com.onlineshop.servlet.security;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.entity.AuthRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UserFilter  implements Filter {
    private SecurityService securityService = ServiceLocator.get(SecurityService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    // Pattern chain of responsibility
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) (request);
        HttpServletResponse httpServletResponse = (HttpServletResponse) (response);

        String requestURI = httpServletRequest.getRequestURI();

        if (requestURI.equals("/login") ||
                requestURI.equals("/registration") ||
                requestURI.startsWith("/assets/")
                || (requestURI.equals("/logout"))) {
            chain.doFilter(request, response);
        } else {
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies == null){
                httpServletResponse.sendRedirect("/login");
                return;
            }

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    String token = cookie.getValue();
                    Session session = securityService.getSessionByToken(token);

                    if (session != null) {
                        AuthRequestWrapper authRequestWrapper = new AuthRequestWrapper(httpServletRequest, session);
                        chain.doFilter(authRequestWrapper, response);
                        return;
                    }
                }
            }

            // log required
            httpServletResponse.sendRedirect("/login");
        }

    }

    @Override
    public void destroy () {

    }
}