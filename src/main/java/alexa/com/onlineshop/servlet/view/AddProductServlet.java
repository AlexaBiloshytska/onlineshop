package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.AuthPrincipal;
import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import alexa.com.onlineshop.ServiceLocator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static alexa.com.onlineshop.entity.Role.ADMIN;

@WebServlet(urlPatterns = "/products/add")
public class AddProductServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.get(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateProcessor.process();
        engine.process("addProduct", new Context(), response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();
        User user = session.getUser();
        if (user.getRole().equals(ADMIN)){
            Integer id = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            String productBrand = request.getParameter("productBrand");
            String description = request.getParameter("productDescription");
            Integer stock = Integer.parseInt(request.getParameter("productStock"));
            Integer price = Integer.parseInt(request.getParameter("productPrice"));
            String image = request.getParameter("productImage");

            Product product = new Product();
            product.setId(id);
            product.setProductName(productName);
            product.setProductBrand(productBrand);
            product.setDescription(description);
            product.setStock(stock);
            product.setPrice(price);
            product.setImageSource(image);

            productService.add(product);

            response.sendRedirect("/products");
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendRedirect("/login");
    }


}
