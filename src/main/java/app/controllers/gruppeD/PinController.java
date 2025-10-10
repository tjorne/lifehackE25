// Package
package app.controllers.gruppeD;

// Imports
import app.entities.gruppeD.Pin;
import app.entities.gruppeD.User;
import app.db.gruppeD.Database;
import app.persistence.gruppeD.PinMapper;
import io.javalin.http.Context;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class PinController {

    // Attributes

    // ________________________________________

    public static void createPin(Context ctx) {

        try (Connection connection = Database.getConnection()) {

            User currentUser = ctx.sessionAttribute("currentUser");
            if (currentUser == null) {
                ctx.status(401).result("Not logged in");
                return;
            }

            JSONObject body = new JSONObject(ctx.body());
            String category = body.optString("category", "visited");
            String title = body.optString("title", "Untitled").trim();
            int rating = body.optInt("rating", 0);
            double lat = body.getDouble("lat");
            double lng = body.getDouble("lng");


            int categoryId = switch (category.toLowerCase()) {
                case "visited" -> 1;
                case "hated" -> 2;
                case "bucket" -> 3;
                default -> 1; // fallback
            };

            PinMapper mapper = new PinMapper(connection);
            Pin pin = mapper.addPin(currentUser.getId(), categoryId, lat, lng, Timestamp.from(Instant.now()), title, rating);

            ctx.json(pin);

        } catch (SQLException e) {

            e.printStackTrace();
            ctx.redirect("/gruppeD/?error=500");

        }
    }

    // ________________________________________

    public static void deletePin(Context ctx) {
        try (Connection connection = Database.getConnection()) {

            int id = Integer.parseInt(ctx.pathParam("id"));

            PinMapper mapper = new PinMapper(connection);
            mapper.deletePin(id);

            ctx.status(204);

        } catch (SQLException e) {

            e.printStackTrace();
            ctx.redirect("/gruppeD/?error=500");

        }
    }

    // ________________________________________

    public static void getPins(Context ctx) {
        try (Connection connection = Database.getConnection()) {

            User currentUser = ctx.sessionAttribute("currentUser");

            if (currentUser == null) {
                ctx.status(401).result("Not logged in");
                return;
            }

            PinMapper mapper = new PinMapper(connection);
            ArrayList<Pin> pins = mapper.getPinsByUserId(currentUser.getId());

            ctx.json(pins);

        } catch (SQLException e) {

            e.printStackTrace();
            ctx.redirect("/gruppeD/?error=500");

        }

    }

}