package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.service.UserService;

import java.util.*;

public class DefaultSecurityService implements SecurityService {
    public List<Session> sessions = new ArrayList<>();
    private UserService userService;
    private Set<String> TOKENS = new HashSet<>();
    int maxAgeSeconds;

    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }

    public void addToken(String token) {
        TOKENS.add(token);
    }

    public boolean isValid(String token) {
        return TOKENS.contains(token);
    }

//    public Session getSessionByToken(String token) {
//        return new Session();
//
//    }

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

    public Session auth(String email,String password){
        User user = userService.auth(email, password);

        int hash = (email + password + user.getSalt()).hashCode();

//        if (hash == user.getHash()) {
//            String token = UUID.randomUUID().toString();
//            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(60);
//
//            Session session = new Session(token, user, expiryDate);
//            sessionList.add(session);
//            return session;
//        }
        return null;
    }

}
