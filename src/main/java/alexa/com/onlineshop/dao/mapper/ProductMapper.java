package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String productName = resultSet.getString("product_name");
        String type = resultSet.getString("product_type");
        String description = resultSet.getString("description");
        Integer stock = resultSet.getInt("stock");
        Integer price = resultSet.getInt("price");
        String image = resultSet.getString("image_source");

        Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setProductType(type);
        product.setDescription(description);
        product.setStock(stock);
        product.setPrice(price);
        product.setImageSource(image);
        return product;
    }



}

