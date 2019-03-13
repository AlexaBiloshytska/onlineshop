package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.entity.Product;
import alexa.com.onlineshop.service.ProductService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import alexa.com.onlineshop.ServiceLocator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/products/add")
public class AddProductServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.get(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String page = TemplateProcessor.getInstance().getPage("addProduct.html", new HashMap<>());
//        response.getWriter().write(page);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        String productType = request.getParameter("productType");
        String description = request.getParameter("productDescription");
        Integer stock = Integer.parseInt(request.getParameter("productStock"));
        Integer price = Integer.parseInt(request.getParameter("productPrice"));
        String image = request.getParameter("productImage");

        Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setProductType(productType);
        product.setDescription(description);
        product.setStock(stock);
        product.setPrice(price);
        product.setImageSource(image);

        productService.add(product);

        response.sendRedirect("/products");
    }
}
