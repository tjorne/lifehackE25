package app.entities.splitit;

public enum Currency {
    DKK("DKK"),
    SEK("SEK"),
    NOK("NOK"),
    USD("$"),
    EUR("€"),
    GBP("£");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
