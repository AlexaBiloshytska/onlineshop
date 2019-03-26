package alexa.com.onlineshop.dao;

import alexa.com.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    void add(Product product);
    Product getById(int id);
    List<Product> searchByName(String name);
    void delete(int id);

    List<Product> getByCategory(int id);
}
