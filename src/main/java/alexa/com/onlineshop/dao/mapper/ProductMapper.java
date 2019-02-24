package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String productName = resultSet.getString("product");
        String type = resultSet.getString("productType");
        String description = resultSet.getString("description");
        Integer stock = resultSet.getInt("stock");
        Integer price = resultSet.getInt("price");
        String image = resultSet.getString("image");

        Product product = new Product();
        product.setId(id);
        product.setProductname(productName);
        product.setProducttype(type);
        product.setDescription(description);
        product.setStock(stock);
        product.setPrice(price);
        product.setImageSource(image);
        return product;
    }



}

