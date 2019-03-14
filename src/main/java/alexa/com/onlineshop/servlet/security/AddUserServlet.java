package alexa.com.onlineshop.servlet.security;

import alexa.com.onlineshop.ServiceLocator;
import alexa.com.onlineshop.entity.User;
import alexa.com.onlineshop.service.UserService;
import alexa.com.onlineshop.service.impl.SessionService;
import alexa.com.onlineshop.templater.TemplateProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class AddUserServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private UserService userService = ServiceLocator.get(UserService.class);
    private SessionService sessionService = ServiceLocator.get(SessionService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateProcessor.process();
        engine.process("registration", new Context(), response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        userService.add(user);


    }


}
