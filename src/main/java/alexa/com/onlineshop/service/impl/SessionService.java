package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class SessionService {
    private UserService userService;
    private List<String> TOKENS = new ArrayList<>();

    public SessionService(UserService userService) {
        this.userService = userService;
    }

    public void addToken(String token){
        TOKENS.add(token);
    }

    public boolean isValid(String token){
        return TOKENS.contains(token);
    }


}
