package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.Category;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.service.CategoryService;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.templater.TemplateProcessor;
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

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private ProductService productService = ServiceLocator.get(ProductService.class);
    private String requestedPage = "product.html";
    private CategoryService categoryService = ServiceLocator.get(CategoryService.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String requestUrl = request.getPathInfo();
        String[] split = requestUrl.split("/");
        int productId = Integer.parseInt(split[1]);

        List<Category> categories = categoryService.getAll();
        Product product = productService.getById(productId);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("product", product);
        pageVariables.put("categories", categories);

        IContext context = new Context(Locale.getDefault(), pageVariables);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        TemplateEngine config = TemplateProcessor.process();
        config.process(requestedPage, context, response.getWriter());
    }


}
