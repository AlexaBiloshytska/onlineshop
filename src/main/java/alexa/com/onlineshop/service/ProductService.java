package alexa.com.onlineshop.service;

import alexa.com.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    void  add(Product product);
    Product getById(int id);

}
