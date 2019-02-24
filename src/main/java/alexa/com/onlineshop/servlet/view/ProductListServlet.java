package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.templater.PageGenerator;
import alexa.com.onlineshop.ServiceLocator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/products")
public class ProductListServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.get(ProductService.class);
    private String requestedPage ="products.html";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[info] getting all products");

        List<Product> products = productService.getAll();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);

        PageGenerator instance= PageGenerator.getInstance();
        String page = instance.getPage(requestedPage, pageVariables);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(page);
    }
}