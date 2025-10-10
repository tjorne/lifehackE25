package app.entities.heltsort;

public class Gips {
    private int withFiltPrice;
    private int withoutFiltPrice;

    public Gips(int withFiltPrice, int withoutFiltPrice) {
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
            return "Ilægining af papirstrimmel\n1x Spartel af samlinger\n1x Fuldspartling\nSlibning & grunding\n2x Maling";
        } else {
            return "Ilægining af papirstrimmel\n1x Spartel af samlinger\n1x Fuldspartling\nSlibning & grunding\nOpsætning af filt\n2x Maling";
        }
    }
}