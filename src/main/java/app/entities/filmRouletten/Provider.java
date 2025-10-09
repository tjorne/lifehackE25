package app.entities.filmRouletten;

public class Provider {
    private int providerId;
    private String providerName;
    private String providerUrl;

    public Provider(int providerId, String providerName, String providerUrl) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.providerUrl = providerUrl;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderUrl() {
        return providerUrl;
    }
}
