package alexa.com.onlineshop.dao.jdbc;

import alexa.com.onlineshop.dao.CategoryDao;
import alexa.com.onlineshop.dao.mapper.CategoryMapper;
import alexa.com.onlineshop.entity.Category;
import com.alexa.jdbc.JdbcTemplate;
import com.alexa.jdbc.template.NamedParameterJDBCTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCategoryDao implements CategoryDao {
    private static final CategoryMapper CATEGORY_MAPPER = new CategoryMapper();
    private static final String GET_ALL_SQL = "select id,name from categories";
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Category> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, CATEGORY_MAPPER);
    }
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}