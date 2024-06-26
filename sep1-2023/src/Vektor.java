import java.util.ArrayList;

public class Vektor {
    private ArrayList<Double> elementi;
    private int n;

    public Vektor(int n) {
        this.n = n;
        this.elementi = new ArrayList<>(n);
    }

    public Vektor(ArrayList<Double> elementi) {
        this.elementi = elementi;
        this.n = elementi.size();
    }

    public Vektor() {
        this(0);
    }

    public Vektor(Vektor v) {
        this.n = v.n;
        this.elementi = new ArrayList<>(v.elementi);
    }

    public Vektor(double[] ds) {
        this.elementi = new ArrayList<>(ds.length);
        this.n = ds.length;
        for (double d : ds) {
            this.elementi.add(d);
        }
    }

    public Double get(int i) {
        return elementi.get(i);
    }

    public Double getLast() {
        return elementi.getLast();
    }

    public ArrayList<Double> getElementi() {
        return elementi;
    }

    public int velicina() {
        return n;
    }

    public void dodajElement(Double e) {
        elementi.add(e);
        this.n += 1;
    }

    public Double uzmiElement(int ind) {
        if (ind >= elementi.size()) {
            throw new IndexOutOfBoundsException("Indeks prevelik");
        }
        return elementi.get(ind);
    }

    public Vektor podVektor(int i, int j) {
        if (i < 0 || j >= elementi.size()) {
            throw new IllegalArgumentException("Opseg nije validan");
        }
        int numRRm = elementi.size() - j - 1;

        ArrayList<Double> ret = new ArrayList<>(this.elementi);
        for (int k = 1; k <= i; k++) {
            ret.removeFirst();
        }
        for (int k = 1; k <= numRRm; k++) {
            ret.removeLast();
        }
        return new Vektor(ret);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < elementi.size()-1; i++) {
            sb.append(elementi.get(i)).append(", ");
        }
        sb.append(elementi.getLast());
        sb.append("]");
        return sb.toString();
    }
}
