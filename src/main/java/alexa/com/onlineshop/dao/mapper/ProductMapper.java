package alexa.com.onlineshop.dao.mapper;

import alexa.com.onlineshop.entity.Product;
import com.alexa.jdbc.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String productName = resultSet.getString("product_name");
        String brand = resultSet.getString("brand");
        String description = resultSet.getString("description");
        Integer stock = resultSet.getInt("stock");
        Integer price = resultSet.getInt("price");
        String image = resultSet.getString("image_source");

        Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setProductBrand(brand);
        product.setDescription(description);
        product.setStock(stock);
        product.setPrice(price);
        product.setImageSource(image);
        return product;
    }



}

