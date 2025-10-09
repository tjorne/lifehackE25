package app.entities.splitit;

public class CurrencyFormatter {

    private static Currency currency = Currency.DKK;

    public static String format(double amount){
        return String.format("%s %.2f,-", currency, amount);
    }

    public static void setCurrency(Currency newCurrency){
        currency = newCurrency;
    }
}
