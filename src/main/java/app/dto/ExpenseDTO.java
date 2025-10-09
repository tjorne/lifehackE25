package app.dto;

import app.entities.splitit.CurrencyFormatter;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ExpenseDTO {
    private int expenseId;
    private String description;
    private double amount;
    private Timestamp createdAt;
    private String userName;
    private String groupName;

    public ExpenseDTO(int expenseId, String description, double amount,
                      Timestamp createdAt, String userName, String groupName) {
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
        this.userName = userName;
        this.groupName = groupName;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String format(){
        return CurrencyFormatter.format(this.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseDTO that = (ExpenseDTO) o;
        return expenseId == that.expenseId && Double.compare(amount, that.amount) == 0 && Objects.equals(description, that.description) && Objects.equals(createdAt, that.createdAt) && Objects.equals(userName, that.userName) && Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, description, amount, createdAt, userName, groupName);
    }

    public String getRelativeTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime postTime = createdAt.toLocalDateTime();
        Duration duration = Duration.between(postTime, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 1) return "Now";
        if (minutes < 60) return minutes + " min ago";
        if (hours < 24) return hours + " hours ago";
        if (days < 7) return days + " days ago";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return postTime.format(formatter);
    }
}

