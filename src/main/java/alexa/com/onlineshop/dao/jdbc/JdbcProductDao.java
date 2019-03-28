package alexa.com.onlineshop.dao.jdbc;

import alexa.com.onlineshop.dao.ProductDao;
import org.slf4j.Logger;
import alexa.com.onlineshop.dao.mapper.ProductMapper;
import alexa.com.onlineshop.entity.Product;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao  implements ProductDao {
    private static final ProductMapper PRODUCT_MAPPER = new ProductMapper();
    public static final String GET_ALL_SQL = "select id," +
            "product_name," +
            "brand," +
            "description," +
            "stock," +
            "price," +
            " image_source from product";
    public static final String ADD_PRODUCT_SQL = "insert into product (product_name, brand, description, stock,price,image_source,category_id) values (?,?,?,?,?,?,?)";

    public static final String GET_BY_ID_SQL =
            "select * from product where id = ?";

    public static final String SEARCH_PRODUCT_SQL =
            "select * from product where product_name like ?";

    public static final String DELETE_PRODUCT_SQL ="delete from product p where p.id = ?";
    public static final String GET_PRODUCT_BY_CATEGORY ="select * from product p join categories c on p.category_id  = c.id where c.id=?";

    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final Logger LOG = LoggerFactory.getLogger(JdbcProductDao.class);

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);) {
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

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getProductBrand());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setInt(5, product.getPrice());
            preparedStatement.setString(6, product.getImageSource());
            preparedStatement.setInt(7,product.getCategoryId());
            preparedStatement.execute();


            System.out.println("Data is successfully inserted: " + product);

        } catch (Exception e) {
            System.out.println("Unable to insert product" + product);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getByCategory(int id) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_CATEGORY)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = PRODUCT_MAPPER.mapRow(resultSet);
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to get product with categoryId = "+id, e );
        }


    }


    @Override
    public Product getById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            LOG.info("Getting product by id= {}", id);
            LOG.debug("Query: {}", GET_BY_ID_SQL);

            if (resultSet.next()) {
                return PRODUCT_MAPPER.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable get product with id: " + id, e);
        }
        return null;
    }

    @Override
    public List<Product> searchByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCT_SQL);) {

            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_MAPPER.mapRow(resultSet);
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to get product", e);
        }
    }

}