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
        private static final String GET_ALL_SQL = "SELECT id, firstname,lastname email,password  FROM USERS ";
        private static final String ADD_USER_SQL = "INSERT INTO users (id, firstname,lastname email, password)VALUES (?,?,?,?)";

        private DataSource dataSource;

        public JdbcUserDao(DataSource dataSource){
            this.dataSource = dataSource;
        }

        public List<User> getAll() {
            List<User> users = new ArrayList<>();
            try (Statement statement = dataSource.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);){
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

        public void add(User user){
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL);) {
                preparedStatement.setInt(1,user.getId());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setString(4,user.getEmail());
                preparedStatement.setString(5,user.getPassword());
                preparedStatement.execute();
                connection.commit();

                System.out.println("Data is successfully inserted: "+ user);
            } catch (Exception e) {
                System.out.println("Failed to insert new user"+ user);
                throw new RuntimeException(e);
            }
        }

    }




