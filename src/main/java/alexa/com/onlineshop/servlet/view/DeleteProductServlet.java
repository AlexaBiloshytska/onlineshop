package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.AuthPrincipal;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.service.impl.DefaultProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static alexa.com.onlineshop.entity.Role.ADMIN;

@WebServlet(urlPatterns = "/product/delete")
public class DeleteProductServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.get(DefaultProductService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();

        if (session != null && session.getUser().getRole().equals(ADMIN)) {
            int id = Integer.parseInt(request.getParameter("id"));
            List<Product> products = session.getCart();

            productService.delete(id);
        }
        response.sendRedirect("/admin/products");
    }
}