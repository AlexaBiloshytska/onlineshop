package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.dao.CategoryDao;
import alexa.com.onlineshop.entity.Category;
import alexa.com.onlineshop.service.CategoryService;

import java.util.List;

public class DefaultCategoryService implements CategoryService {
    private CategoryDao categoryDao;

    public DefaultCategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}
