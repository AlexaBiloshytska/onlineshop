package alexa.com.onlineshop.dao;

import alexa.com.onlineshop.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao {
    private static final DefaultUserDao INSTANCE = new DefaultUserDao();
    private List<User> users = new ArrayList<>();

    private DefaultUserDao(){
        users.add(new User(1, "Alex","Gold","user@gmail.com","password" ));
    }

    public static DefaultUserDao getInstance(){
        return INSTANCE;
    }

    public boolean isValid(String username, String password){
        return true;
    }

}
