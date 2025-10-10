// Package
package app.controllers.gruppeD;

// Imports
import app.entities.gruppeD.User;
import app.db.gruppeD.Database;
import app.persistence.gruppeD.UserMapper;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {

    // Attributes

    // ______________________________________________

    public static void handleLogin(Context ctx) {

        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        // Validation
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            ctx.redirect("/gruppeD/?error=missingFields");
            return;
        }

        try (Connection connection = Database.getConnection()) {

            UserMapper userMapper = new UserMapper(connection);
            User user = userMapper.getUserByUsername(username);

            if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
                ctx.redirect("/gruppeD/?error=wrongInfo");
                return;
            }

            // Set session
            ctx.sessionAttribute("currentUser", user);

            // Redirect if success
            ctx.status(200).redirect("/gruppeD/worldmap");

        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/?error=500");
        }
    }

} // LoginController end