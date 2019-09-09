package alexa.com.onlineshop.servlet.assets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@WebServlet("/assets/*")
public class AssetsServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AssetsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(requestURI);
        if (resourceAsStream == null) {
            throw new RuntimeException("Resource not found: " + requestURI);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
        StringBuilder fileAsText = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            fileAsText.append(line);
        }
        response.getWriter().write(fileAsText.toString());
    }
}
