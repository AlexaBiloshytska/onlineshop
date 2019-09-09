package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.dao.UserDao;
import alexa.com.onlineshop.dao.jdbc.JdbcUserDao;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.UserService;

import java.util.List;
import java.util.logging.Logger;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public boolean isValid(String email, String password) {
        return userDao.checkUserExistence(email, password);
    }

    @Override
    public User auth(String surname, String password) {
        return new User();

    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public void setUserDao(JdbcUserDao userDao) {
        this.userDao = userDao;
    }

}
