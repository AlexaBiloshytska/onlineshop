package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.Role;
import alexa.com.onlineshop.entity.User;
import com.alexa.jdbc.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    public User mapRow(ResultSet resultSet) throws SQLException {
        // Retrieve by column name
        Integer id = resultSet.getInt("id");
        String first = resultSet.getString("first_name");
        String last = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String salt = resultSet.getString("salt");
        String hash = resultSet.getString("hash");
        Role role = Role.valueOf(resultSet.getString("role")) ;
        // fill list
        User user = new User();
        user.setId(id);
        user.setFirstName(first);
        user.setLastName(last);
        user.setEmail(email);
        user.setSalt(salt);
        user.setHash(hash);
        user.setRole(role);
        return user;
    }

}
