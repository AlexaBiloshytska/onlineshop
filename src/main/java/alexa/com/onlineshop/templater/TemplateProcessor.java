package alexa.com.onlineshop.templater;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateProcessor {

    private static TemplateEngine templateEngine;

    public static TemplateEngine  process(){
        if (templateEngine == null ){
            templateEngine = config();
        }
        return templateEngine;
    }

    private static TemplateEngine config(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

}