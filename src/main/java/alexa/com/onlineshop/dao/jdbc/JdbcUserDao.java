package alexa.com.onlineshop.dao.jdbc;

import alexa.com.onlineshop.dao.UserDao;
import alexa.com.onlineshop.dao.mapper.UserMapper;
import alexa.com.onlineshop.entity.User;
import com.alexa.jdbc.JdbcTemplate;
import com.alexa.jdbc.template.NamedParameterJDBCTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUserDao implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcProductDao.class);
    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final String GET_ALL_SQL = "SELECT id, first_name,last_name email FROM USERS ";
    private static final String ADD_USER_SQL = "INSERT INTO USERS(first_name, last_name, email, hash, salt)" +
            " VALUES (:first_name, :last_name, :email, :hash, :salt)";
    private static final String GET_USER_SQL = "SELECT * FROM USERS WHERE email = ? ";
    private static final String GET_USER_BY_EMAIL = "SELECT id,first_name, last_name, email, salt, hash, role from USERS " +
            "where email=?";

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, USER_MAPPER);
    }

    public void add(User user) {
        try {
            Map<String, Object> map = new HashMap<>();

            map.put(":first_name", user.getFirstName());
            map.put(":last_name", user.getLastName());
            map.put(":email", user.getEmail());
            map.put(":hash", user.getHash());
            map.put(":salt", user.getSalt());

            jdbcTemplate.update(ADD_USER_SQL, map);
            LOG.info("User is successfully inserted: " + user);
        } catch (Exception e) {
            LOG.error("Failed to insert new user" + user);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUserExistence(String email, String password) {
        List<User> users = jdbcTemplate.query(GET_USER_SQL, USER_MAPPER, email, password);
        return !users.isEmpty();
    }

    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, USER_MAPPER, email);
    }
}




