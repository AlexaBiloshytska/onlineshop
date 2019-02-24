package alexa.com.onlineshop.dao;

import alexa.com.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    void add(Product product);

}
