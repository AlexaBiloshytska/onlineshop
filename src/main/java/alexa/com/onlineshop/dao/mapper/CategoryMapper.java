package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper {
    public  Category mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String categoryName = resultSet.getString("name");

        Category category = new Category();
        category.setId(id);
        category.setName(categoryName);
        return category;

    }
}

