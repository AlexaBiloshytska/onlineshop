package alexa.com.onlineshop;

import alexa.com.onlineshop.dao.CategoryDao;
import alexa.com.onlineshop.dao.ProductDao;
import alexa.com.onlineshop.dao.UserDao;
import alexa.com.onlineshop.dao.jdbc.*;
import alexa.com.onlineshop.service.CategoryService;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.service.impl.DefaultCategoryService;
import alexa.com.onlineshop.service.impl.DefaultProductService;
import alexa.com.onlineshop.service.impl.DefaultSecurityService;
import alexa.com.onlineshop.service.impl.DefaultUserService;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.service.UserService;

import com.alexa.jdbc.JdbcTemplate;
import com.alexa.jdbc.template.NamedParameterJDBCTemplate;
import org.postgresql.ds.PGPoolingDataSource;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> LOCATOR = initDefaultDependencies();

    public static void register(Class<?> clazz, Object service) {
        LOCATOR.put(clazz, service);

    }
    public static Map<Class<?>, Object> initDefaultDependencies() {
        Map<Class<?>, Object> map = new HashMap<>();

        //config DAO
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setServerName("ec2-54-228-224-37.eu-west-1.compute.amazonaws.com");
        source.setDatabaseName("d6hd4raa7s2md8");
        source.setUser("rvtwtvhohmgbpq");
        source.setPassword("79b60efd633d2197e8db5f053b3b1f6a223bed447eaddbbf63c89ab3bd2c1991");
        source.setSsl(true);
        source.setSslfactory("org.postgresql.ssl.NonValidatingFactory");
        source.setPortNumber(5432);
        source.setMaxConnections(10);

        JdbcTemplate jdbcTemplate = new NamedParameterJDBCTemplate(source);

        UserDao userDao = new JdbcUserDao(jdbcTemplate);
        ProductDao productDao = new JdbcProductDao(jdbcTemplate);
        CategoryDao categoryDao = new JdbcCategoryDao(jdbcTemplate);

        /*
        <bean id="jdbcUserDao" class="alexa.com.onlineshop.dao.jdbc.JdbcUserDao">
        </bean>

        <bean id="defaultUserService" class="alexa.com.onlineshop.dao.jdbc.DefaultUserService">
            <param="UserDao" ref="jdbcUserDao" >
        </bean>
         */

        // config services
        UserService userService = new DefaultUserService(userDao);
        map.put(UserService.class, userService);

        ProductService productService = new DefaultProductService(productDao);
        map.put(ProductService.class,productService);

        CategoryService categoryService = new DefaultCategoryService(categoryDao);
        map.put(CategoryService.class,categoryService);

        // Add sessionService
        SecurityService sessionService = new DefaultSecurityService();
        map.put(SecurityService.class, sessionService);

        return map;

    }
    public static <T> T get(Class<?> clazz){
        return (T) clazz.cast(LOCATOR.get(clazz));
    }
}
