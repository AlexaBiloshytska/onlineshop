package alexa.com.onlineshop.dao;

import alexa.com.onlineshop.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DefaultProductDao {
    private static final DefaultProductDao INSTANCE = new DefaultProductDao();
    private List<Product> products = new ArrayList<>();

    private DefaultProductDao(){
        Product product = new Product();
        product.setId(1);
        product.setProductName("Computer");
        product.setProductType("Electronics");
        product.setDescription("A new laptop of Asus Zenbook series");
        product.setStock(50);
        product.setPrice(1000);
        product.setImageSource("asus");
        
        products.add(product);
        
    }

    public static DefaultProductDao getInstance(){
        return INSTANCE;
    }

    public List<Product> getAll(){
        return new ArrayList<>(products);
    }

    public void save(Product product){
        products.add(product);
    }
}
