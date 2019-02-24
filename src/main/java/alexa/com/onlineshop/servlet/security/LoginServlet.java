package alexa.com.onlineshop.servlet.security;

import alexa.com.onlineshop.service.UserService;
import alexa.com.onlineshop.templater.PageGenerator;
import alexa.com.onlineshop.ServiceLocator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static alexa.com.onlineshop.templater.PageGenerator.getInstance;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet{

    private UserService userService = ServiceLocator.get(UserService.class);
    public static List<String> tokens = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = getInstance();
        String page = PageGenerator.getInstance().getPage("login.html", new HashMap<>());
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");



        boolean valid = userService.isValid(username,password);
        if (!valid){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect("/login");
        } else{
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user-token", token);
            tokens.add(token);

            response.addCookie(cookie);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/products");
        }
    }
}
