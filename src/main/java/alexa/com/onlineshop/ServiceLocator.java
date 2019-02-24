package alexa.com.onlineshop;

import alexa.com.onlineshop.dao.ProductDao;
import alexa.com.onlineshop.dao.UserDao;
import alexa.com.onlineshop.dao.jdbc.*;
import alexa.com.onlineshop.service.impl.DefaultProductService;
import alexa.com.onlineshop.service.impl.DefaultUserService;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.service.UserService;
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
        source.setServerName("localhost");
        source.setDatabaseName("postgres");
        source.setUser("postgres");
        source.setPassword("postgres");
        source.setPortNumber(5432);
        source.setMaxConnections(10);

        UserDao userDao = new JdbcUserDao(source);
        ProductDao productDao = new JdbcProductDao(source);

        // config services
        UserService userService = new DefaultUserService(userDao);
        map.put(UserService.class, userService);

        ProductService productService = new DefaultProductService(productDao);
        map.put(ProductService.class,productService);

        return map;

    }
    public static <T> T get(Class<?> clazz){
        return (T) clazz.cast(LOCATOR.get(clazz));
    }
}
