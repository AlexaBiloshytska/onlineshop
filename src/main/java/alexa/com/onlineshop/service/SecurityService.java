package alexa.com.onlineshop.service;

import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;

public interface SecurityService {
    Session getSessionByToken(String token);

    Session getSession(String token);

    Session delete(String token);

    void save(Session session);

    Session getSession(User user);
}
