package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.*;
import alexa.com.onlineshop.service.CategoryService;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.templater.TemplateProcessor;
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

import static alexa.com.onlineshop.entity.Role.ADMIN;


@WebServlet(urlPatterns = "/admin/products")
public class AdminServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.get(ProductService.class);
    private CategoryService categoryService = ServiceLocator.get(CategoryService.class);
    private String requestedPage ="admin.html";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();
        User user = session.getUser();
        if (!user.getRole().equals(ADMIN)) {
            response.sendRedirect("/login");
            return;
        }
        List<Category> categories = categoryService.getAll();
        List<Product> products = productService.getAll();
        List<Product> cart = session.getCart();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);
        pageVariables.put("categories", categories);
        pageVariables.put("user", user);
        pageVariables.put("cardSize", cart.size());

        TemplateEngine config = TemplateProcessor.process();
        IContext context = new Context(Locale.getDefault(), pageVariables);
        config.process(requestedPage, context, response.getWriter());

    }
}
