package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.*;
import alexa.com.onlineshop.service.CategoryService;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.service.impl.DefaultCategoryService;
import alexa.com.onlineshop.service.impl.DefaultProductService;
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
    private ProductService productService = ServiceLocator.get(DefaultProductService.class);
    private CategoryService categoryService = ServiceLocator.get(DefaultCategoryService.class);
    private String requestedPage ="product-list.html";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("[info] getting all products");
        System.out.println("NOTED");
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();
        User user = session.getUser();
        List<Product> cart = session.getCart();

        List<Product> products;
        String productName = request.getParameter("productName");
        String queryString = request.getQueryString();

        if (productName != null) {
            products = productService.search(productName);
        } else if (queryString != null){
            Map<String, String> queryMap = getQueryMap(queryString);
            String categoryId = queryMap.get("category");

            Integer id = Integer.parseInt(categoryId);
            products = productService.getByCategory(id);
        } else {
            products = productService.getAll();
        }

        List<Category> categories = categoryService.getAll();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);
        pageVariables.put("user", user);
        pageVariables.put("cardSize", cart.size());
        pageVariables.put("categories", categories);


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

    private Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}