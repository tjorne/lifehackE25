// Package
package dk.project.server.controllers;

// Imports
import dk.project.User;
import dk.project.db.Database;
import dk.project.mapper.UserMapper;
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
            ctx.redirect("/?error=missingFields");
            return;
        }

        try (Connection connection = Database.getConnection()) {

            UserMapper userMapper = new UserMapper(connection);
            User user = userMapper.getUserByUsername(username);

            if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
                ctx.redirect("/?error=wrongInfo");
                return;
            }

            // Set session
            ctx.sessionAttribute("currentUser", user);

            // Redirect if success
            ctx.status(200).redirect("/worldmap");

        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/?error=500");
        }
    }

} // LoginController end