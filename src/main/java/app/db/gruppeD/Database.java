// Package
package app.db.gruppeD;

// Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Attributes
    private static final String URL = "jdbc:postgresql://localhost:5433/Beyondborders"; // Jeg bruger 33.
    private static final String USER = "postgres"; // mit setup
    private static final String PASSWORD = "dinmor"; // mit setup

    // ________________________________________________

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

} // Database end