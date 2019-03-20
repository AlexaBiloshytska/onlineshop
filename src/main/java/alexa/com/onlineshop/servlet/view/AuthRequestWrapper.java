package alexa.com.onlineshop.servlet.view;


import alexa.com.onlineshop.entity.AuthPrincipal;
import alexa.com.onlineshop.entity.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;


public class AuthRequestWrapper extends HttpServletRequestWrapper{
    private Principal principal;

    public AuthRequestWrapper(HttpServletRequest request, Session session) {
        super(request);
        this.principal = new AuthPrincipal(session);
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }
}
