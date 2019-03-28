package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.*;
import alexa.com.onlineshop.service.CategoryService;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import alexa.com.onlineshop.ServiceLocator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.servlet.ServletException;
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

@WebServlet(urlPatterns = "/products/add")
public class AddProductServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.get(ProductService.class);
    private CategoryService categoryService = ServiceLocator.get(CategoryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();
        User user = session.getUser();

        List<Product> cart = session.getCart();
        List<Category> categories = categoryService.getAll();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("categories", categories);
        pageVariables.put("user", user);
        pageVariables.put("cardSize", cart.size());

        IContext context = new Context(Locale.getDefault(), pageVariables);

        TemplateEngine engine = TemplateProcessor.process();
        engine.process("addProduct", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = ((AuthPrincipal) request.getUserPrincipal()).getSession();
        User user = session.getUser();
        if (user.getRole().equals(ADMIN)){
            String productName = request.getParameter("productName");
            String productBrand = request.getParameter("productBrand");
            String description = request.getParameter("description");
            Integer stock = Integer.parseInt(request.getParameter("stock"));
            Integer price = Integer.parseInt(request.getParameter("price"));
            String image = request.getParameter("imageSource");
            Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));

            Product product = new Product();
            product.setProductName(productName);
            product.setProductBrand(productBrand);
            product.setDescription(description);
            product.setStock(stock);
            product.setPrice(price);
            product.setImageSource(image);
            product.setCategoryId(categoryId);

            productService.add(product);

            response.sendRedirect("/products");
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendRedirect("/login");
    }


}
