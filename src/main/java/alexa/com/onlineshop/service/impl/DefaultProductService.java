package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.dao.ProductDao;
import alexa.com.onlineshop.dao.jdbc.JdbcProductDao;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService {
    private ProductDao productDao;

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public void add(Product product) {
         productDao.add(product);

    }


    @Override
    public Product getById(int id) {
        return productDao.getById(id);
    }

    @Override
    public List<Product> search(String name) {
        return productDao.searchByName(name);
    }

    @Override
    public void delete(int id) {
         productDao.delete(id);
    }

    @Override
    public List<Product> getByCategory(int id) {
        return productDao.getByCategory(id);
    }

    public void setProductDao(JdbcProductDao productDao) {
        this.productDao = productDao;
    }
}
