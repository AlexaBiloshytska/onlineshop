package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.service.ProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet(urlPatterns = "/cart")
public class AddToCardServlet extends HttpServlet {
    private ProductService productService;

    protected void doPost(HttpServletRequest request, HttpServletResponse  response){
        int id = Integer.parseInt(request.getParameter("id"));
        //(AuthRequestWrapper)request.
        // I should know who initiated request
        request.getUserPrincipal();

        Product product = new Product();
        product.setId(id);

        
        try {
            response.sendRedirect("/products");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
