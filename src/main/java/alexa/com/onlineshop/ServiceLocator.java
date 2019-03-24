package alexa.com.onlineshop;

import alexa.com.onlineshop.dao.ProductDao;
import alexa.com.onlineshop.dao.UserDao;
import alexa.com.onlineshop.dao.jdbc.*;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.service.impl.DefaultProductService;
import alexa.com.onlineshop.service.impl.DefaultSecurityService;
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
        source.setServerName("ec2-54-228-224-37.eu-west-1.compute.amazonaws.com");
        source.setDatabaseName("d6hd4raa7s2md8");
        source.setUser("rvtwtvhohmgbpq");
        source.setPassword("79b60efd633d2197e8db5f053b3b1f6a223bed447eaddbbf63c89ab3bd2c1991");
        source.setSsl(true);
        source.setSslfactory("org.postgresql.ssl.NonValidatingFactory");
        source.setPortNumber(5432);
        source.setMaxConnections(10);

        UserDao userDao = new JdbcUserDao(source);
        ProductDao productDao = new JdbcProductDao(source);

        // config services
        UserService userService = new DefaultUserService(userDao);
        map.put(UserService.class, userService);

        ProductService productService = new DefaultProductService(productDao);
        map.put(ProductService.class,productService);

        // Add sessionService
        SecurityService sessionService = new DefaultSecurityService();
        map.put(SecurityService.class, sessionService);

        return map;

    }
    public static <T> T get(Class<?> clazz){
        return (T) clazz.cast(LOCATOR.get(clazz));
    }
}
