package app.persistence.splitit;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private static ConnectionPool testPool;
    private UserMapper userMapper;

    @BeforeAll
    static void setUpDatabase(){
        testPool = ConnectionPool.getInstance(
                "postgres",
                "postgres",
                "jdbc:postgresql://localhost:5432/spiltit?currentSchema=test",
                "lifehack"
        );

        try (Connection con = testPool.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users CASCADE");
            stmt.execute("CREATE TABLE users (" +
                    "user_id SERIAL PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "password VARCHAR(100) NOT NULL" +
                    ")");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }


    @BeforeEach
    void setUp() {

        userMapper = new UserMapper();

        try (Connection con = testPool.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("DELETE FROM users CASCADE");

            stmt.execute("ALTER SEQUENCE users_user_id_seq RESTART WITH 1");

            stmt.execute("INSERT INTO users (name, password) VALUES " +
                    "('Daniel', '1234'), " +
                    "('Morten', 'abcd'), " +
                    "('Jesper', 'pass'), " +
                    "('Toby', '123abc')");

        } catch (SQLException e) {
            fail("Database setup failed: " + e.getMessage());
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(testPool.getConnection());
    }

    @Test
    void testLogin() throws DatabaseException {
        User user = userMapper.login("Morten", "abcd");
        assertNotNull(user);
        assertEquals("Morten", user.getUserName());

        User wrongUser = userMapper.login("Morten", "wrongpass");
        assertNull(wrongUser);
    }

    @Test
    void testCreateUser() throws DatabaseException {
       userMapper.createuser("Laura","1234");
        assertEquals(user,userMapper.getUserById(user.getUserId()));
    }

    @Test
    void testGetAllUsers() throws DatabaseException {
        List<User> users = userMapper.getAllUsers();
        assertEquals(4, users.size());
    }


    @Test
    void getUserById() throws DatabaseException {
        User expected = new User(3,"Jesper","pass");
        User actual = userMapper.getUserById(3);
        assertEquals(expected,actual);
    }


}