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
import java.time.LocalDateTime;

public class RegisterController {

    // Attributes

    // ________________________________________________________________

    public static void handleRegister(Context ctx) {

        // Gets inserted values
        String username = ctx.formParam("username");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String passwordConfirm = ctx.formParam("password_confirm");

        // Validation
        if (username == null || email == null || password == null || passwordConfirm == null) {
            // Notification later
            ctx.redirect("/register?error=missingFields");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            // Notification later
            ctx.redirect("/register?error=wrongPassMatch");
            return;
        }

        // Static method Try-Catch for SQL Errors
        try (Connection connection = Database.getConnection()) {

            UserMapper userMapper = new UserMapper(connection);
            User existingUser = userMapper.getUserByUsername(username);

            if (existingUser != null) {

                ctx.redirect("/register?error=accountExists");
                return;

            }

            // Hash password
            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

            // Create new user and insert in our database!
            User user = new User(0, username, email, passwordHash, 1, LocalDateTime.now(), true, "en");
            userMapper.insertUser(user);

            // Redirect back to login
            ctx.redirect("/?error=accountCreated");

        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/register?error=500");

        }
    }

} // RegisterController end