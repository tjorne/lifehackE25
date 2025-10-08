package app.controllers.gruppeD;

// Imports
import app.entities.gruppeD.User;
import app.db.gruppeD.Database;
import app.persistence.gruppeD.UserMapper;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;

public class SettingsController {

    // __________________________________________________________

    public static void handleDeleteAccount(Context ctx) {

        String confirmation = ctx.formParam("confirmation");
        String name = ctx.formParam("name");

   
        if (confirmation == null || name == null) {
            ctx.redirect("/gruppeD/settings?error=deleteMissingFields");
            return;
        }

        if (!"I am so sexy".equals(confirmation)) {
            ctx.redirect("/gruppeD/settings?error=deleteConfirmMismatch");
            return;
        }

        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser == null) {
            ctx.redirect("/gruppeD/settings?error=deleteNotLoggedIn");
            return;
        }

        if (!currentUser.getUsername().equals(name)) {
            ctx.redirect("/gruppeD/settings?error=deleteNameMismatch");
            return;
        }

        // Delete fra databasen
        try (Connection connection = Database.getConnection()) {

            UserMapper userMapper = new UserMapper(connection);
            userMapper.deleteUser(currentUser.getId());

            ctx.req().getSession().invalidate();
            ctx.redirect("/gruppeD/?error=accountDeleted");

        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/settings?error=deleteError");
        }
    }

    // __________________________________________________________

    public static void handleChangeUsername(Context ctx) {
        String newName = ctx.formParam("name");
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/gruppeD/settings?error=deleteNotLoggedIn");
            return;
        }

        try (Connection connection = Database.getConnection()) {
            UserMapper userMapper = new UserMapper(connection);
            User existingUser = userMapper.getUserByUsername(newName);
            if(existingUser != null) {
                ctx.redirect("/gruppeD/settings?error=accountExists");
                return;
            }

            currentUser.setUsername(newName);

            userMapper.updateUser(currentUser);

            ctx.sessionAttribute("currentUser", currentUser);

            ctx.redirect("/gruppeD/settings?error=usernameChanged");


        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/settings?error=UsernameError");
        }



    }

    // __________________________________________________________

    public static void handleChangePassword(Context ctx) {
        String oldPassword = ctx.formParam("password");
        String newPassword = ctx.formParam("newPassword");
        String confirmPassword = ctx.formParam("newPasswordConfirm");
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/gruppeD/settings?error=deleteNotLoggedIn");
            return;
        }

        try (Connection connection = Database.getConnection()) {
            UserMapper userMapper = new UserMapper(connection);
            if (!BCrypt.checkpw(oldPassword, currentUser.getPasswordHash())) {
                ctx.redirect("/gruppeD/settings?error=wrongPassword");
                return;
            }
            if(!newPassword.equals(confirmPassword)) {
                ctx.redirect("/gruppeD/settings?error=passwordMismatch");
                return;
            }

            currentUser.setPasswordHash(newPassword);

            userMapper.updateUser(currentUser);

            ctx.sessionAttribute("currentUser", currentUser);

            ctx.redirect("/gruppeD/settings?error=wrongPassMatch");


        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/settings?error=UsernameError");
        }



    }

    // __________________________________________________________

    public static void handleChangeEmail(Context ctx) {
        String password = ctx.formParam("password");
        String newEmail = ctx.formParam("newEmail");
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/gruppeD/settings?error=deleteNotLoggedIn");
            return;
        }

        try (Connection connection = Database.getConnection()) {
            UserMapper userMapper = new UserMapper(connection);
            if(!currentUser.getPasswordHash().equals(password)) {
                ctx.redirect("/gruppeD/settings?error=wrongPassword");
                return;
            }

            currentUser.setEmail(newEmail);

            userMapper.updateUser(currentUser);

            ctx.sessionAttribute("currentUser", currentUser);

            ctx.redirect("/gruppeD/settings?error=emailChanged");


        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/settings?error=UsernameError");
        }



    }

    // __________________________________________________________

    public static void changeNotifications(Context ctx) {
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/gruppeD/settings?error=deleteNotLoggedIn");
            return;
        }

        try (Connection connection = Database.getConnection()) {
            UserMapper userMapper = new UserMapper(connection);

            currentUser.setNotifications(!currentUser.getNotifications());

            userMapper.updateUser(currentUser);
            ctx.sessionAttribute("currentUser", currentUser);


        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/settings?error=UsernameError");
        }



    }

    // __________________________________________________________

    public static void changeLanguage(Context ctx) {
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/gruppeD/settings?error=deleteNotLoggedIn");
            return;
        }

        try (Connection connection = Database.getConnection()) {
            UserMapper userMapper = new UserMapper(connection);
            String lang = ctx.formParam("language");

            if (lang == null || lang.isEmpty() || currentUser.getLanguage().equals(lang)) {
                ctx.redirect("/gruppeD/settings?error=UsernameError");
                return;
            }

            currentUser.setLanguage(lang);

            userMapper.updateUser(currentUser);
            ctx.sessionAttribute("currentUser", currentUser);


        } catch (SQLException e) {
            e.printStackTrace();
            ctx.redirect("/gruppeD/settings?error=UsernameError");
        }



    }
}
