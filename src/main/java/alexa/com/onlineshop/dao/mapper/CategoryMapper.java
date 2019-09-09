package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.Category;
import alexa.com.onlineshop.entity.Product;
import com.alexa.jdbc.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    public Category mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String categoryName = resultSet.getString("name");

        Category category = new Category();
        category.setId(id);
        category.setName(categoryName);
        return category;

    }
}

