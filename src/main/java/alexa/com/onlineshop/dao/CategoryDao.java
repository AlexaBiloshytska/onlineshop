package alexa.com.onlineshop.dao;

import alexa.com.onlineshop.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
}
