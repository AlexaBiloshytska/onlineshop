package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.templater.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session curSession = (Session) req.getAttribute("session"); // ?

        if (curSession != null) {
            List<Product> products = curSession.getCart();

//            TemplateProcessor templateProcessor = TemplateProcessor.getInstance();
//            Map<String, Object> map = new HashMap<>();
//            map.put("products", products);
//            String page = templateProcessor.getPage("card", map);

//            resp.getWriter().write(page);
        } else {
            resp.sendRedirect("/login");
        }
    }
}
