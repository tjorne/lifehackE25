package app.persistence.splitit;

import app.entities.splitit.Expense;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseMapperTest {
    private static ConnectionPool testPool;
    private ExpenseMapper expenseMapper;

    @BeforeAll
    static void setUpDatabase() {
        testPool = ConnectionPool.getInstance(
                "postgres",
                "postgres",
                "jdbc:postgresql://localhost:5432/spiltit?currentSchema=test",
                "lifehack"
        );

        try (Connection con = testPool.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS expense CASCADE");
            stmt.execute("DROP TABLE IF EXISTS group_users CASCADE");
            stmt.execute("DROP TABLE IF EXISTS groups CASCADE");
            stmt.execute("DROP TABLE IF EXISTS users CASCADE");

            stmt.execute("CREATE TABLE users (" +
                    "user_id SERIAL PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            stmt.execute("CREATE TABLE groups (" +
                    "group_id SERIAL PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            stmt.execute("CREATE TABLE group_users (" +
                    "user_id INT NOT NULL," +
                    "group_id INT NOT NULL," +
                    "PRIMARY KEY (user_id, group_id)," +
                    "CONSTRAINT group_users_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE," +
                    "CONSTRAINT group_users_group_id_fkey FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE" +
                    ")");

            stmt.execute("CREATE TABLE expense (" +
                    "expense_id SERIAL PRIMARY KEY," +
                    "user_id INT NOT NULL," +
                    "group_id INT NOT NULL," +
                    "amount DECIMAL(10,2) NOT NULL," +
                    "description VARCHAR(255)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "CONSTRAINT expense_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE," +
                    "CONSTRAINT expense_group_id_fkey FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE" +
                    ")");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        expenseMapper = new ExpenseMapper(testPool);

        try (Connection con = testPool.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("DELETE FROM expense");
            stmt.execute("DELETE FROM group_users");
            stmt.execute("DELETE FROM groups");
            stmt.execute("DELETE FROM users");

            stmt.execute("ALTER SEQUENCE expense_expense_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE users_user_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");

            stmt.execute("INSERT INTO users (name, password) VALUES " +
                    "('Alice', 'pass123'), " +
                    "('Bob', 'pass456'), " +
                    "('Charlie', 'pass789'), " +
                    "('David', 'pass000')");


            stmt.execute("INSERT INTO groups (name) VALUES " +
                    "('Weekendtur'), " +
                    "('Fredagsbar'), " +
                    "('Fødselsdagsfest')");


            stmt.execute("INSERT INTO group_users (user_id, group_id) VALUES " +
                    "(1, 1), (2, 1), " +
                    "(3, 2), " +
                    "(4, 3)");


            stmt.execute("INSERT INTO expense (user_id, group_id, amount, description) VALUES " +
                    "(1, 1, 150.50, 'Øl'), " +
                    "(2, 1, 200.00, 'Mad'), " +
                    "(3, 3, 75.25, 'Taxa'), " +
                    "(3, 3, 300.00, 'Drinks')");

        } catch (SQLException e) {
            fail("Database setup failed: " + e.getMessage());
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(testPool.getConnection());
    }

    @Test
    void testCreateExpense() throws DatabaseException {
        Expense expense = expenseMapper.createExpense(1,1,"Negroni's",185.50);
        assertNotNull(expense);
        Expense actual = expenseMapper.getExpenseById(5);
        assertEquals(expense.getExpenseId(),actual.getExpenseId());
        assertEquals(expense.getUserId(),actual.getUserId());
        assertEquals(expense.getDescription(),actual.getDescription());
        assertEquals(expense.getAmount(),actual.getAmount());
    }

    @Test
    void testGetExpenseById() throws DatabaseException {
        Expense expense = expenseMapper.getExpenseById(2);
        assertNotNull(expense);
        Expense nonExpense = expenseMapper.getExpenseById(6);
        assertNull(nonExpense);
        assertEquals(expense.getAmount(),200.00);
        assertEquals(expense.getDescription(),"Mad");
    }

    @Test
    void testGetExpensesByGroupId() throws DatabaseException {
        List<Expense> expenses = expenseMapper.getExpensesByGroupId(1);
        assertNotNull(expenses);
        assertEquals(2, expenses.size());

        for (Expense expense : expenses) {
            assertEquals(1, expense.getGroupId());
        }
    }

    @Test
    void testGetExpensesByUserId() throws DatabaseException {
        List<Expense> expenses = expenseMapper.getExpensesByUserId(3);
        assertNotNull(expenses);
        assertEquals(2, expenses.size());
        assertEquals("Taxa", expenses.get(0).getDescription());
        assertEquals("Drinks", expenses.get(1).getDescription());
    }

    @Test
    void getExpensesByUserAndGroup() throws DatabaseException {
        List<Expense> expenses = expenseMapper.getExpensesByUserAndGroup(1, 1);
        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        assertEquals("Øl", expenses.get(0).getDescription());
        assertEquals(150.50, expenses.get(0).getAmount(), 0.01);
    }

    @Test
    void testUpdateExpense() throws DatabaseException {
        Expense expense = expenseMapper.getExpenseById(3);
        assertNotNull(expense);
        expense.setAmount(155.55);
        boolean isUpdated = expenseMapper.updateExpense(expense.getExpenseId(),expense.getDescription(),expense.getAmount());
        assertTrue(isUpdated);
        Expense updatedExpense = expenseMapper.getExpenseById(3);
        assertEquals(expense.getAmount(),updatedExpense.getAmount());
        assertEquals(expense.getDescription(),updatedExpense.getDescription());
    }

    @Test
    void testDeleteExpense() throws DatabaseException {
        boolean isDeleted = expenseMapper.deleteExpense(1);
        assertTrue(isDeleted);
        Expense expense = expenseMapper.getExpenseById(1);
        assertNull(expense);
    }
}