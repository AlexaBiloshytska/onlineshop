package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.dao.CategoryDao;
import alexa.com.onlineshop.dao.jdbc.JdbcCategoryDao;
import alexa.com.onlineshop.entity.Category;
import alexa.com.onlineshop.service.CategoryService;

import java.util.List;

public class DefaultCategoryService implements CategoryService {
    private CategoryDao categoryDao;

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    public void setCategoryDao(JdbcCategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
}
