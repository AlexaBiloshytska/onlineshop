package alexa.com.onlineshop.dao.jdbc;

import alexa.com.onlineshop.dao.CategoryDao;
import alexa.com.onlineshop.dao.mapper.CategoryMapper;
import alexa.com.onlineshop.entity.Category;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCategoryDao implements CategoryDao {
    private DataSource dataSource;
    private static final CategoryMapper CATEGORY_MAPPER = new CategoryMapper();
    private static final String GET_ALL_SQL = "select id,name from categories";

    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);) {
            while (resultSet.next()) {
                Category category = CATEGORY_MAPPER.mapRow(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}