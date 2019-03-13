package alexa.com.onlineshop.dao;

import alexa.com.onlineshop.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();

    void add(User user);

    boolean checkUserExistence(String email, String password);
}
