package alexa.com.onlineshop.dao.jdbc;

import alexa.com.onlineshop.dao.ProductDao;

import alexa.com.onlineshop.dao.mapper.ProductMapper;
import alexa.com.onlineshop.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao  implements ProductDao {
    private static final ProductMapper  PRODUCT_MAPPER = new ProductMapper();
    public static final String GET_ALL_SQL ="select id," +
            "product_name," +
            "product_type," +
            "description," +
            "stock," +
            "price," +
            " image_source from product";
    public static final String ADD_PRODUCT_SQL="insert into products (product_id," +
            "product_name," +
            "product_type," +
            "description," +
            "stock,price," +
            " image_source)values (?,?,?,?,?,?,?)";
    private Connection connection;
    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);){
            while (resultSet.next()) {
                Product product = PRODUCT_MAPPER.mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println("[ERROR] Unable to process resultSet");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_SQL);) {
            preparedStatement.setInt(1,product.getId());
            preparedStatement.setString(2,product.getProductName());
            preparedStatement.setString(3,product.getProductType());
            preparedStatement.setString(4,product.getDescription());
            preparedStatement.setInt(5,product.getStock());
            preparedStatement.setInt(6,product.getPrice());
            preparedStatement.setString(7,product.getImageSource());
            connection.commit();

            System.out.println("Data is successfully inserted: "+ product);

        } catch (Exception e) {
            System.out.println("Unable to insert product" + product);
            throw new RuntimeException(e);
        }
    }
}
