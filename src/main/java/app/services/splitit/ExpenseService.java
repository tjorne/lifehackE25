package app.services.splitit;

import app.dto.ExpenseDTO;
import app.entities.splitit.Expense;
import app.exceptions.DatabaseException;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(int userId, int groupId, String description, double amount) throws DatabaseException;
    ExpenseDTO getExpenseById(int expenseId) throws DatabaseException;
    boolean updateExpense(int expenseId, String description, double amount) throws DatabaseException;
    boolean deleteExpense(int expenseId) throws DatabaseException;
    public boolean deleteAllExpensesByGroupId(int groupId) throws DatabaseException;

    List<ExpenseDTO> getExpensesByGroupId(int groupId) throws DatabaseException;
    List<ExpenseDTO> getExpensesByUserId(int userId) throws DatabaseException;
    List<ExpenseDTO> getExpensesByUserAndGroup(int userId, int groupId) throws DatabaseException;

}
