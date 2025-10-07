package app.dto;

import java.util.Objects;

public class UserBalanceDTO {
    private String userName;
    private double balance;

    public UserBalanceDTO(String userName, double balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserBalanceDTO that = (UserBalanceDTO) o;
        return Double.compare(balance, that.balance) == 0 && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, balance);
    }
}
