package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.AuthPrincipal;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;
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
    private String requestedPage ="admin.html";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();
        User user = session.getUser();
        if (!user.getRole().equals(ADMIN)) {
            response.sendRedirect("/login");
            return;
        }

        List<Product> products = productService.getAll();
        TemplateEngine config = TemplateProcessor.process();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);
        IContext context = new Context(Locale.getDefault(), pageVariables);
        config.process(requestedPage, context, response.getWriter());

    }
}
