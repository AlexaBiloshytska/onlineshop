package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
        // Retrieve by column name
        Integer id = resultSet.getInt("id");
        String first = resultSet.getString("firstName");
        String last = resultSet.getString("lastName");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");


        // fill list
        User user = new User();
        user.setId(id);
        user.setFirstName(first);
        user.setLastName(last);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

}
