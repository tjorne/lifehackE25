package app.services.splitit;

import app.dto.ExpenseDTO;
import app.entities.User;
import app.entities.splitit.Expense;
import app.entities.splitit.Group;
import app.exceptions.DatabaseException;
import app.persistence.UserMapper;
import app.persistence.splitit.ExpenseMapper;
import app.persistence.splitit.GroupMapper;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseMapper expenseMapper;
    private GroupMapper groupMapper;

    public ExpenseServiceImpl(ExpenseMapper expenseMapper, GroupMapper groupMapper) {
        this.expenseMapper = expenseMapper;
        this.groupMapper = groupMapper;
    }

    @Override
    public Expense createExpense(int userId, int groupId, String description, double amount) throws DatabaseException {
        validateDescription(description);
        validateAmount(amount);

        if (groupMapper.getGroupById(groupId) == null) {
            throw new DatabaseException("Group does not exist");
        }
        if (UserMapper.getUserById(userId) == null) {
            throw new DatabaseException("User does not exist");
        }

        return expenseMapper.createExpense(userId,groupId,description,amount);
    }

    @Override
    public ExpenseDTO getExpenseById(int expenseId) throws DatabaseException {
        Expense expense = expenseMapper.getExpenseById(expenseId);
        User user = UserMapper.getUserById(expense.getUserId());
        Group group = groupMapper.getGroupById(expense.getGroupId());

        return new ExpenseDTO(expenseId,
                expense.getDescription(),
                expense.getAmount(),
                expense.getCreatedAt(),
                user.getUserName(),
                group.getName());
    }

    @Override
    public boolean updateExpense(int expenseId, String description, double amount) throws DatabaseException {
        return false;
    }

    @Override
    public boolean deleteExpense(int expenseId) throws DatabaseException {
        return false;
    }

    @Override
    public List<ExpenseDTO> getExpensesByGroupId(int groupId) throws DatabaseException {
        return List.of();
    }

    @Override
    public List<ExpenseDTO> getExpensesByUserId(int userId) throws DatabaseException {
        return List.of();
    }

    @Override
    public List<ExpenseDTO> getExpensesByUserAndGroup(int userId, int groupId) throws DatabaseException {
        return List.of();
    }

    private void validateDescription(String description)
    {
        if(description == null ||description.isEmpty()){
            throw new  IllegalArgumentException ("Description cannot be empty");
        }
    }

    private void validateAmount(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount cannot be below zero");
        }
    }
}
