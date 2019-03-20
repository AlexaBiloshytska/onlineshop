package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
        // Retrieve by column name
        Integer id = resultSet.getInt("id");
        String first = resultSet.getString("first_name");
        String last = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String salt = resultSet.getString("salt");
        String hash = resultSet.getString("hash");
        // fill list
        User user = new User();
        user.setId(id);
        user.setFirstName(first);
        user.setLastName(last);
        user.setEmail(email);
        user.setSalt(salt);
        user.setHash(hash);
        return user;
    }

}
