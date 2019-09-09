package alexa.com.onlineshop.servlet.security;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.Session;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.SecurityService;
import alexa.com.onlineshop.service.UserService;
import alexa.com.onlineshop.service.impl.DefaultSecurityService;
import alexa.com.onlineshop.service.impl.DefaultUserService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import org.apache.commons.codec.digest.DigestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;


@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final int MAX_SESSION_AGE_SEC = 6000; // TODO: create property file in resource folder
    private UserService userService = ServiceLocator.get(DefaultUserService.class);
    private SecurityService sessionService = ServiceLocator.get(DefaultSecurityService.class); // Use interface instead

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateProcessor.process();
        engine.process("login", new Context(), response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.getUserByEmail(email);
        if (user != null) {
            String userHash = user.getHash();
            String salt = user.getSalt();
            String original = salt+email+password;
            String requestHash =  DigestUtils.sha256Hex(original);

            if (userHash.equals(requestHash)){
                Session session = sessionService.getSession(user);

                String token;
                if (session != null) { // If session exist
                    token = session.getToken();
                    session.setExpireDate(LocalDateTime.now().plusSeconds(MAX_SESSION_AGE_SEC));
                } else { // If no active session create new
                    token = UUID.randomUUID().toString();
                    LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(MAX_SESSION_AGE_SEC);
                    sessionService.save(new Session(token, user, localDateTime));
                }

                Cookie cookie = new Cookie("user-token", token);
                cookie.setMaxAge(MAX_SESSION_AGE_SEC);

                response.addCookie(cookie);
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/products");

            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/login");
            }
        }
    }

}
