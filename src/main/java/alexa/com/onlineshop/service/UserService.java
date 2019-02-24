package alexa.com.onlineshop.service;

import alexa.com.onlineshop.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    void  add(User user);

    boolean isValid(String surname, String password);
}
