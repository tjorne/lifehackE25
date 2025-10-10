package app.entities.heltsort;

public class Premalet {
    private int oneCoatPrice;
    private int twoCoatsPrice;

    public Premalet(int oneCoatPrice, int twoCoatsPrice) {
        this.oneCoatPrice = oneCoatPrice;
        this.twoCoatsPrice = twoCoatsPrice;
    }

    public int getOneCoatPrice() {
        return oneCoatPrice;
    }

    public int getTwoCoatsPrice() {
        return twoCoatsPrice;
    }

    public String workDescription(boolean twoCoats) {
        if (!twoCoats) {
            return "1x Pletspartling af vægge\n1x Slibning & pletmaling\n1x Fuldmaling";
        } else {
            return "1x Pletspartling af vægge\n1x Slibning\n2x Fuldmaling";
        }
    }
}
