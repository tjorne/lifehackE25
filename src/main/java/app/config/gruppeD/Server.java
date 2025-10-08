// Package
package dk.project.server;

// Imports
import dk.project.server.routes.ErrorRoutes;
import dk.project.server.routes.PageRoutes;
import dk.project.server.routes.ValidRoutes;
import io.javalin.Javalin;
import java.sql.SQLException;

public class Server {

    // Attributes
    private Javalin app;

    // __________________________________________________

    public void start(int port) throws SQLException {

        // Resource folder
        app = Javalin.create(config -> {
            config.staticFiles.add("/static"); // folder i resources/static
        }).start(port);

        // __________________________________________________

        new PageRoutes(app); // GET
        new ValidRoutes(app); // POST
        new ErrorRoutes(app); // ERROR

        // System print
        System.out.println("http://localhost:" + port + " | I din URL bby girl");
    }

    // __________________________________________________

    public void stop() {
        if (app != null) {
            app.stop();
        }
    }

} // Server end