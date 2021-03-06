package alexa.com.onlineshop.servlet.security;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.entity.AuthRequestWrapper;
import alexa.com.onlineshop.service.impl.DefaultCategoryService;
import alexa.com.onlineshop.service.impl.DefaultSecurityService;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UserFilter  implements Filter {
    private SecurityService securityService = ServiceLocator.get(DefaultSecurityService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    // Pattern chain of responsibility
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) (request);
        HttpServletResponse httpServletResponse = (HttpServletResponse) (response);

        String requestURI = httpServletRequest.getRequestURI();

        if (requestURI.equals("/login") ||
                requestURI.equals("/logout") ||
                requestURI.equals("/registration") ||
                requestURI.startsWith("/assets/") ||
                requestURI.startsWith("/favicon.ico")
                ){
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
//                        MDC.put("USER_KEY", session.getUser().getFirstName());
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