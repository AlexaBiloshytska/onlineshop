package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.service.UserService;

import java.util.*;

public class DefaultSecurityService implements SecurityService {
    private List<Session> sessions = new ArrayList<>();
    private int maxAgeSeconds;

    public Session getSessionByToken(String token) {
        for ( Session session :sessions ){
            if (session.getToken().equals(token)){
                return session;
            }

        }
        return null;
    }

    public Session getSession(String token) {
        for (Session session : sessions) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }
        return null;
    }

    public Session delete(String token) {
        Iterator<Session> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getToken().equals(token)) {
                iterator.remove();
            }
        }
        return null;
    }

    public void save(Session session){
        sessions.add(session);
    }


    public int getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(int maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

    public Session getSession(User user) {
        for (Session session : sessions) {
            if (session.getUser().equals(user)) {
                return session;
            }
        }
        return null;
    }


}
