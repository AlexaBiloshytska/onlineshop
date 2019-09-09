package alexa.com.onlineshop;

import com.alexa.ioc.context.ClassPathApplicationContext;
import org.postgresql.ds.PGPoolingDataSource;

public class ServiceLocator {
    private static ClassPathApplicationContext classPathApplicationContext = new ClassPathApplicationContext();

    public static <T> T get(Class<T> clazz) {
        return classPathApplicationContext.getBean(clazz);
    }
}
