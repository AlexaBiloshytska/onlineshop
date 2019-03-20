package alexa.com.onlineshop.servlet.view;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.UserService;
import alexa.com.onlineshop.service.impl.DefaultSecurityService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/registration")
public class AddUserServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private UserService userService = ServiceLocator.get(UserService.class);
    private DefaultSecurityService sessionService = ServiceLocator.get(DefaultSecurityService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateProcessor.process();
        engine.process("addUser", new Context(), response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String salt = UUID.randomUUID().toString();
        String original = salt+email+password;
        String hash = DigestUtils.sha256Hex(original);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setSalt(salt);
        user.setHash(hash);

        userService.add(user);

       response.sendRedirect("/login");
    }



}
