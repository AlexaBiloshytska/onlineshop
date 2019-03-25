package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.AuthPrincipal;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import alexa.com.onlineshop.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@WebServlet(urlPatterns = "/products")
public class ProductListServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ProductService productService = ServiceLocator.get(ProductService.class);
    private String requestedPage ="product-list.html";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("[info] getting all products");
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();
        User user = session.getUser();
        List<Product> cart = session.getCart();

        String productName = request.getParameter("productName");

        List<Product> products;
        if (productName != null){
            products = productService.search(productName);
        } else {
            products = productService.getAll();
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);
        pageVariables.put("user", user);
        pageVariables.put("cardSize", cart.size());


        IContext context = new Context(Locale.getDefault(), pageVariables);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        TemplateEngine config = TemplateProcessor.process();
        config.process(requestedPage, context, response.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestURI = request.getRequestURI();

            Integer id = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            String productBrand = request.getParameter("productBrand");
            String description = request.getParameter("productDescription");
            Integer stock = Integer.parseInt(request.getParameter("productStock"));
            Integer price = Integer.parseInt(request.getParameter("productPrice"));

            Product product = new Product();
            product.setId(id);
            product.setProductName(productName);
            product.setProductBrand(productBrand);
            product.setDescription(description);
            product.setStock(stock);
            product.setPrice(price);

            productService.add(product);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}