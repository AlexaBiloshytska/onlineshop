package alexa.com.onlineshop.dao.jdbc;

import alexa.com.onlineshop.dao.UserDao;
import alexa.com.onlineshop.dao.mapper.UserMapper;
import alexa.com.onlineshop.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final String GET_ALL_SQL = "SELECT id, first_name,last_name email,  FROM USERS ";
    private static final String ADD_USER_SQL = "INSERT INTO USERS(first_name, last_name, email, hash, salt) VALUES (?,?,?,?,?)";
    private static final String GET_USER_SQL = "SELECT * FROM USERS WHERE email = ? ";
    private static final String GET_USER_BY_EMAIL = "SELECT id,first_name, last_name, email, salt, hash, role from USERS where email=?";

    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL)) {
            while (resultSet.next()) {
                User user = USER_MAPPER.mapRow(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println("[ERROR] Unable to process resultSet");
            throw new RuntimeException(e);
        }
    }

    public void add(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getHash());
            preparedStatement.setString(5,user.getSalt());


            preparedStatement.execute();

            System.out.println("Data is successfully inserted: " + user);
        } catch (Exception e) {
            System.out.println("Failed to insert new user" + user);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUserExistence(String email, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_SQL)){
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public User getUserByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return USER_MAPPER.mapRow(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}




