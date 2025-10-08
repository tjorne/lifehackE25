// Package
package app.config.gruppeD;

// Imports
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.io.StringWriter;
import java.util.Map;

public class ThymeleafSetup {

    // Attributes
    private static final TemplateEngine templateEngine;
    private static String suffix = ".html";
    private static String prefix = "templates/gruppeD/";

    // __________________________________________________________

    // Runs without access modifier or name as it runs once per call (which is once lol)..
    static {

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(prefix);
        resolver.setSuffix(suffix);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false); // Så skal Main.java ikke genstartes ved hver ændring.

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

    }

    // __________________________________________________________

    public static String render(String templateName, Map<String, Object> variables) {

        Context context = new Context();

        if (variables != null) {
            variables.forEach(context::setVariable);
        }

        StringWriter writer = new StringWriter();

        templateEngine.process(templateName, context, writer);

        return writer.toString();

    }

} // ThymeleafSetup end