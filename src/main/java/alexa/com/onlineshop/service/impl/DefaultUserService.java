package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.dao.UserDao;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    public DefaultUserService(UserDao userDao) {
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void  add(User user) {
        userDao.add(user);
    }

    @Override
    public boolean isValid(String surname, String password) {
        return true;
    }


}
