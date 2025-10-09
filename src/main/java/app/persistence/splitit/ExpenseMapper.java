package app.persistence.splitit;

import app.entities.splitit.Expense;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseMapper {
    private ConnectionPool connectionPool;

    public ExpenseMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Expense> getExpensesByGroupId(int groupId) throws DatabaseException
    {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expense WHERE group_id = ? ORDER BY created_at DESC";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int expenseId = rs.getInt("expense_id");
                int userId = rs.getInt("user_id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                Timestamp createdAt = rs.getTimestamp("created_at");

                expenses.add(new Expense(expenseId, userId, groupId, description, amount, createdAt));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af udgifter for gruppe " + groupId + ": " + e.getMessage());
        }
        return expenses;
    }

    public List<Expense> getExpensesByUserAndGroup(int userId, int groupId) throws DatabaseException
    {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expense WHERE user_id = ? AND group_id = ? ORDER BY created_at DESC";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, groupId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int expenseId = rs.getInt("expense_id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                Timestamp createdAt = rs.getTimestamp("created_at");

                expenses.add(new Expense(expenseId, userId, groupId, description, amount, createdAt));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af udgifter: " + e.getMessage());
        }
        return expenses;
    }


        public Expense createExpense(int userId, int groupId, String description, double amount) throws DatabaseException
        {
            String sql = "INSERT INTO expense (user_id, group_id, description, amount) VALUES (?,?,?,?)";
            Expense expense = null;

            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, userId);
                ps.setInt(2, groupId);
                ps.setString(3,description);
                ps.setDouble(4,amount);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    int expenseId = rs.getInt(1);
                    Timestamp createdAt = rs.getTimestamp("created_at");

                    expense = new Expense(expenseId, userId, groupId, description, amount, new Timestamp(System.currentTimeMillis()));
                    }
            } catch (SQLException e) {
                throw new DatabaseException("Kunne ikke oprette udgift " + e.getMessage());
            }
            return expense;
        }

        public Expense getExpenseById(int expenseId) throws DatabaseException
        {
            Expense expense = null;
            String sql = "select * from expense e join users u on u.user_id = e.user_id WHERE e.expense_id = ?";

            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, expenseId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int groupId = rs.getInt("group_id");
                    String description = rs.getString("description");
                    double amount = rs.getDouble("amount");
                    Timestamp timeStamp = rs.getTimestamp("created_at");
                    expense = new Expense(expenseId,userId,groupId,description,amount, timeStamp);
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl ved hentning af udgift med id = " + expenseId + e.getMessage());
            }
            return expense;
        }

    public List<Expense> getExpensesByUserId(int userId) throws DatabaseException
    {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expense WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int expenseId = rs.getInt("expense_id");
                int groupId = rs.getInt("group_id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                Timestamp createdAt = rs.getTimestamp("created_at");

                expenses.add(new Expense(expenseId, userId, groupId, description, amount, createdAt));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af udgifter for bruger " + userId + ": " + e.getMessage());
        }
        return expenses;
    }

        public boolean updateExpense(int expenseId, String description, double amount) throws DatabaseException
        {

            String sql = "UPDATE expense SET description = ?, amount = ? WHERE expense_id = ?";
            boolean result = false;

            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, description);
                ps.setDouble(2, amount);
                ps.setInt(3,expenseId);

                int rowsAffected = ps.executeUpdate();

                if(rowsAffected == 1){
                    result = true;
                }

            } catch (SQLException e) {
                throw new DatabaseException("Kunne ikke opdatere udgift " + e.getMessage());
            }
            return result;
        }


        public boolean deleteExpense(int expenseId) throws DatabaseException
        {
            String sql = "delete from expense where expense_id = ?";
            boolean result = false;

            try (Connection connection = connectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, expenseId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    result = true;
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl ved sletning af en udgift" + e.getMessage());
            }
            return result;
        }

    public boolean deleteAllExpensesByGroupId(int groupId) throws DatabaseException
    {
        String sql = "DELETE FROM expense WHERE group_id = ?";
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                result = true;
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ved sletning af udgifter: " + e.getMessage());
        }
        return result;
    }

}

