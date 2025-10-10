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
import java.time.LocalDateTime;

public class RegisterController {

    // Attributes

    // ________________________________________________________________

    public static void handleRegister(Context ctx) {

        String username = ctx.formParam("username");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String passwordConfirm = ctx.formParam("password_confirm");

        if (username == null || email == null || password == null || passwordConfirm == null) {
            ctx.redirect("/gruppeD/register?error=missingFields");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            ctx.redirect("/gruppeD/register?error=wrongPassMatch");
            return;
        }

        try (Connection connection = Database.getConnection()) {

            UserMapper userMapper = new UserMapper(connection);
            User existingUser = userMapper.getUserByUsername(username);

            if (existingUser != null) {

                ctx.redirect("/gruppeD/register?error=accountExists");
                return;

            }

            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User(0, username, email, passwordHash, 1, LocalDateTime.now(), true, "en");
            userMapper.insertUser(user);

            ctx.redirect("/gruppeD/?error=accountCreated");

        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/register?error=500");

        }
    }

} // RegisterController end