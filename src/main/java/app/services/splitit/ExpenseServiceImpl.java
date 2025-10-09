package app.services.splitit;

import app.dto.ExpenseDTO;
import app.entities.User;
import app.entities.splitit.Expense;
import app.entities.splitit.Group;
import app.exceptions.DatabaseException;
import app.persistence.UserMapper;
import app.persistence.splitit.ExpenseMapper;
import app.persistence.splitit.GroupMapper;

import java.util.Comparator;
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

        return new ExpenseDTO(
                expenseId,
                expense.getUserId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCreatedAt(),
                user.getUserName(),
                group.getName());
    }

    @Override
    public boolean updateExpense(int expenseId, String description, double amount) throws DatabaseException {
        validateAmount(amount);
        validateDescription(description);
        return expenseMapper.updateExpense(expenseId,description,amount);
    }

    @Override
    public boolean deleteExpense(int expenseId) throws DatabaseException {
        return expenseMapper.deleteExpense(expenseId);
    }

    @Override
    public boolean deleteAllExpensesByGroupId(int groupId) throws DatabaseException {
    return expenseMapper.deleteAllExpensesByGroupId(groupId);
    }

    @Override
    public List<ExpenseDTO> getExpensesByGroupId(int groupId) throws DatabaseException {
        List<Expense> expenses = expenseMapper.getExpensesByGroupId(groupId);
        Group group = groupMapper.getGroupById(groupId);

        return expenses.stream()
                .map(expense -> {
                    try {
                        return new ExpenseDTO(
                                expense.getExpenseId(),
                                expense.getUserId(),
                                expense.getDescription(),
                                expense.getAmount(),
                                expense.getCreatedAt(),
                                UserMapper.getUserById(expense.getUserId()).getUserName(),
                                group.getName()
                        );
                    } catch (DatabaseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(ExpenseDTO::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<ExpenseDTO> getExpensesByUserId(int userId) throws DatabaseException {
        List<Expense> expenses = expenseMapper.getExpensesByUserId(userId);
        User user = UserMapper.getUserById(userId);

        return expenses.stream()
                .map(expense -> {
                    try {
                        return new ExpenseDTO(
                                expense.getExpenseId(),
                                expense.getUserId(),
                                expense.getDescription(),
                                expense.getAmount(),
                                expense.getCreatedAt(),
                                user.getUserName(),
                                groupMapper.getGroupById(expense.getGroupId()).getName()
                        );
                    } catch (DatabaseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sorted(Comparator.comparing(ExpenseDTO::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<ExpenseDTO> getExpensesByUserAndGroup(int userId, int groupId) throws DatabaseException {
        List<Expense> expenses = expenseMapper.getExpensesByUserAndGroup(userId,groupId);
        User user = UserMapper.getUserById(userId);
        Group group = groupMapper.getGroupById(groupId);

        return expenses.stream()
                .map(expense -> new ExpenseDTO(
                        expense.getExpenseId(),
                        expense.getUserId(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getCreatedAt(),
                        user.getUserName(),
                        group.getName()
                ))
                .sorted(Comparator.comparing(ExpenseDTO::getCreatedAt).reversed())
                .toList();
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
