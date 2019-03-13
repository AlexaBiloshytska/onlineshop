package alexa.com.onlineshop.service.impl;

import alexa.com.onlineshop.dao.ProductDao;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService {
    private ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public void add(Product user) {

    }
    @Override
    public Product getById(int id) {
        return productDao.getById(id);
    }
}
