package app.entities.splitit;

import java.util.Objects;

public class Settlement {
    private String fromUserName;
    private String toUserName;
    private double amount;
    private final String currency = "$";

    public Settlement(String fromUserName, String toUserName, double amount) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.amount = amount;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFormattedDouble(){
        return String.format("%s %.2f,-", this.currency, this.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settlement that = (Settlement) o;
        return Double.compare(amount, that.amount) == 0 &&
                Objects.equals(fromUserName, that.fromUserName) &&
                Objects.equals(toUserName, that.toUserName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUserName, toUserName, amount);
    }

    @Override
    public String toString() {
        return fromUserName + " owes " + toUserName + " " + String.format("%.2f", amount) + " kr";
    }
}