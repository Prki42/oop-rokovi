public class KNNPrediktor {
    private VektorskiProstor vp;
    private Metrika metrika;
    private int k;

    public KNNPrediktor(VektorskiProstor vp, Metrika metrika, int k) {
        this.vp = vp;
        this.metrika = metrika;
        this.k = k;
    }

    public double predvidjanjeCiljnePromene(Vektor targetVektor) {
        vp.getVektori().sort(new KomparatorVektora(targetVektor, metrika));
        double res = 0;
        for (int i = 0; i < k; i++) {
            res += vp.getVektori().get(i).getLast();
        }
        return res / k;
    }

    public void setK(int k) {
        if (vp.getVektori().size() <= k || k <= 0) {
            throw new IllegalArgumentException("K mora biti u [0, "+vp.getVektori().size()+"]");
        }
        this.k = k;
    }

    public void setMetrika(Metrika metrika) {
        this.metrika = metrika;
    }

    public VektorskiProstor getVp() {
        return vp;
    }
}
