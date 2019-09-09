package alexa.com.onlineshop.servlet.view;


import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.*;
import alexa.com.onlineshop.service.CategoryService;
import alexa.com.onlineshop.service.impl.DefaultCategoryService;
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

@WebServlet(urlPatterns = "/cart")
public class CardServlet extends HttpServlet {
    private CategoryService categoryService = ServiceLocator.get(DefaultCategoryService.class);
    private String requestedPage ="product-list.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {

        Session session = ((AuthPrincipal) req.getUserPrincipal()).getSession();
        User user = session.getUser();

        List<Product> cart = session.getCart();
        List<Category> categories = categoryService.getAll();

        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("categories", categories);
        pageVariables.put("user", user);
        pageVariables.put("cardSize", cart.size());
        pageVariables.put("products", cart);

        TemplateEngine config = TemplateProcessor.process();
        IContext context = new Context(Locale.getDefault(), pageVariables);
        config.process(requestedPage, context, response.getWriter());


    }
}