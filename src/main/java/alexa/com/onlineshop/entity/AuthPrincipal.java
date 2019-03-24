package alexa.com.onlineshop.entity;

import java.security.Principal;

public class AuthPrincipal implements Principal {
    private Session session;

    public AuthPrincipal(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public String getName() {
        return session.getUser().getFirstName();
    }
}
