package alexa.com.onlineshop.servlet.security;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.service.UserService;
import alexa.com.onlineshop.service.impl.SessionService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = ServiceLocator.get(UserService.class);
    private SessionService sessionService = ServiceLocator.get(SessionService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        TemplateEngine engine = TemplateProcessor.process();
        engine.process("login", new Context(), response.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean valid = userService.isValid(email, password);

        if (!valid) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect("/login");
        } else {
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user-token", token);

            sessionService.addToken(token);

            response.addCookie(cookie);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/products");
        }
    }
}
