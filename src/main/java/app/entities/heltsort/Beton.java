package app.entities.heltsort;

public class Beton {
    private int withFiltPrice;
    private int withoutFiltPrice;

    public Beton(int withFiltPrice, int withoutFiltPrice) {
        this.withFiltPrice = withFiltPrice;
        this.withoutFiltPrice = withoutFiltPrice;
    }

    public int getWithFiltPrice() {
        return withFiltPrice;
    }


    public int getWithoutFiltPrice() {
        return withoutFiltPrice;
    }


    public String workDescription(boolean filt) {
        if (!filt) {
            return "1x Pletspartling\n2x Fuldspartling\nSlibning & grunding\n2x Maling";
        } else {
            return "1x Pletspartling\n2x Fuldspartling\nSlibning & grunding\nOpsætning af Filt\n2x Maling";
        }
    }
}

