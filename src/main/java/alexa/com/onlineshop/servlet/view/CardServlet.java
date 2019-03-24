package alexa.com.onlineshop.servlet.view;


import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.AuthPrincipal;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.service.impl.DefaultSecurityService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
    private SecurityService defaultSecurityService= ServiceLocator.get(SecurityService.class);
    private String requestedPage ="product-list.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();

        Session session = ((AuthPrincipal) req.getUserPrincipal()).getSession();
        List<Product> products = session.getCart();
        map.put("products", products);
        IContext context = new Context(Locale.getDefault(), map);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        TemplateEngine config = TemplateProcessor.process();
        config.process(requestedPage, context, response.getWriter());


    }
}