package alexa.com.onlineshop.filter;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.service.impl.DefaultSecurityService;
import alexa.com.onlineshop.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UserFilter  implements Filter {
    private UserService userService = ServiceLocator.get(UserService.class);
    private DefaultSecurityService sessionService = ServiceLocator.get(DefaultSecurityService.class);

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
                requestURI.startsWith("/assets/")) {
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
                    // TODO Session session = sessionService.getSessionByToken(getSessionByToken)
                    if (sessionService.isValid(token)) {
                        // TODO: AuthRequestWrapper authRequestWrapper = new AuthRequestWrapper(httpServletRequest , session);
                        chain.doFilter(request, response);
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