package alexa.com.onlineshop.filter;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.service.impl.SessionService;
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
    private SessionService sessionService = ServiceLocator.get(SessionService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) (request);
        HttpServletResponse httpServletResponse = (HttpServletResponse) (response);

        String requestURI = httpServletRequest.getRequestURI();

        if (requestURI.equals("/login") || requestURI.startsWith("/assets/")) {
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
                    if (sessionService.isValid(token)) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }

            httpServletResponse.sendRedirect("/login");
        }

    }

    @Override
    public void destroy () {

    }
}