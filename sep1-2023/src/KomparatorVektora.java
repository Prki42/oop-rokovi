import java.util.Comparator;

public class KomparatorVektora implements Comparator<Vektor> {
    private Vektor targetVektor;
    private Metrika metrika;

    public KomparatorVektora(Vektor targetVektor, Metrika metrika) {
        this.targetVektor = targetVektor;
        this.metrika = metrika;
    }

    @Override
    public int compare(Vektor v1, Vektor v2) {
        Vektor u1 = v1.podVektor(0, v1.velicina() - 2);
        Vektor u2 = v2.podVektor(0, v2.velicina() - 2);
        return Double.compare(metrika.rastojanje(u1, targetVektor), metrika.rastojanje(u2, targetVektor));
    }
}
