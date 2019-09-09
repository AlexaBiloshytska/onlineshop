package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.AuthPrincipal;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.service.impl.DefaultProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/cart/add")
public class AddToCardServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.get(DefaultProductService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse  response){
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();

        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id);
        if (product == null){
            throw new RuntimeException("Unable to add product with id" + id);
        }

        session.addProductToCard(product);
        
        try {
            response.sendRedirect("/products");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
