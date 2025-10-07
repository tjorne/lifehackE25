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

            stmt.execute("DROP TABLE IF EXISTS test.users CASCADE");
            stmt.execute("DROP TABLE IF EXISTS public.users CASCADE");

            stmt.execute("CREATE TABLE public.users (" +
                    "user_id SERIAL PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(100) NOT NULL," +
                    "role VARCHAR(20) DEFAULT 'user' NOT NULL" +
                    ")");

            stmt.execute("CREATE TABLE test.users (" +
                    "user_id SERIAL PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(100) NOT NULL," +
                    "role VARCHAR(20) DEFAULT 'user' NOT NULL" +
                    ")");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection con = testPool.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("DELETE FROM public.users CASCADE");
            stmt.execute("DELETE FROM test.users CASCADE");

            stmt.execute("ALTER SEQUENCE public.users_user_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE test.users_user_id_seq RESTART WITH 1");

            stmt.execute("INSERT INTO public.users (username, password, role) VALUES " +
                    "('Daniel', '1234', 'user'), " +
                    "('Morten', 'abcd', 'user'), " +
                    "('Jesper', 'pass', 'user'), " +
                    "('Toby', '123abc', 'user')");

            stmt.execute("INSERT INTO test.users (username, password, role) VALUES " +
                    "('Daniel', '1234', 'user'), " +
                    "('Morten', 'abcd', 'user'), " +
                    "('Jesper', 'pass', 'user'), " +
                    "('Toby', '123abc', 'user')");

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
        User user = UserMapper.login("Morten", "abcd");
        assertNotNull(user);
        assertEquals("Morten", user.getUserName());
    }

    @Test
    void testLoginFailure() {
        assertThrows(DatabaseException.class, () -> {
            UserMapper.login("Morten", "wrongpass");
        });
    }

    @Test
    void testCreateUser() throws DatabaseException {
        UserMapper.createuser("Laura","1234");
        assertEquals(5, UserMapper.getAllUsers().size());
    }

    @Test
    void testGetAllUsers() throws DatabaseException {
        List<User> users = UserMapper.getAllUsers();
        assertEquals(4, users.size());
    }

    @Test
    void getUserById() throws DatabaseException {
        User actual = UserMapper.getUserById(2);
        assertNotNull(actual);
        assertEquals("Morten", actual.getUserName());
        assertEquals(2, actual.getUserId());
    }


}