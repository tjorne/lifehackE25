package app.entities.splitit;

import java.sql.Timestamp;
import java.util.Objects;

public class Expense {
    private int expenseId;
    private int userId;
    private int groupId;
    private String description;
    private double amount;
    private Timestamp createdAt;

    public Expense(int expenseId, int userId, int groupId, String description, double amount,Timestamp createdAt) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.groupId = groupId;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return expenseId == expense.expenseId && userId == expense.userId && groupId == expense.groupId && Double.compare(amount, expense.amount) == 0 && Objects.equals(description, expense.description) && Objects.equals(createdAt, expense.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, userId, groupId, description, amount, createdAt);
    }
}
