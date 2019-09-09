package alexa.com.onlineshop.dao.jdbc;

import alexa.com.onlineshop.dao.ProductDao;
import alexa.com.onlineshop.dao.mapper.ProductMapper;
import alexa.com.onlineshop.entity.Product;
import com.alexa.jdbc.JdbcTemplate;
import com.alexa.jdbc.template.NamedParameterJDBCTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcProductDao implements ProductDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcProductDao.class);
    private static final ProductMapper PRODUCT_MAPPER = new ProductMapper();
    private static final String GET_ALL_SQL = "select id, product_name, brand, description, stock, price, image_source from product";
    private static final String ADD_PRODUCT_SQL =
            "insert into product (product_name, brand, description, stock,price,image_source,category_id) " +
                    "values (:product_name, :brand, :description, :stock, :price, :image_source, :category_id)";
    private static final String GET_BY_ID_SQL = "select * from product where id = ?";
    private static final String SEARCH_PRODUCT_SQL = "select * from product where product_name like ?";
    private static final String DELETE_PRODUCT_SQL = "delete from product p where p.id = ?";
    private static final String GET_PRODUCT_BY_CATEGORY = "select * from product p join categories c on p.category_id  = c.id where c.id=?";
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Product> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, PRODUCT_MAPPER);
    }

    @Override
    public void add(Product product) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("product_name", product.getProductName());
            map.put("brand", product.getProductBrand());
            map.put("description", product.getDescription());
            map.put("stock", product.getStock());
            map.put("price", product.getPrice());
            map.put("image_source", product.getImageSource());
            map.put("category_id", product.getCategoryId());

            int update = jdbcTemplate.update(ADD_PRODUCT_SQL, map);


            LOG.info("Product is successfully inserted: " + product);
        } catch (Exception e) {
            LOG.error("Unable to insert product" + product);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_PRODUCT_SQL, id);
    }

    @Override
    public List<Product> getByCategory(int id) {
        return jdbcTemplate.query(GET_PRODUCT_BY_CATEGORY, PRODUCT_MAPPER, id);
    }

    @Override
    public Product getById(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID_SQL, PRODUCT_MAPPER, id);
        } catch (Exception e) {
            throw new RuntimeException("Unable get product with id: " + id, e);
        }
    }

    @Override
    public List<Product> searchByName(String name) {
        return jdbcTemplate.query(SEARCH_PRODUCT_SQL, PRODUCT_MAPPER, name);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}