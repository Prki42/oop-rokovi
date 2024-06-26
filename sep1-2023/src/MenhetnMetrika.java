public class MenhetnMetrika implements Metrika {
    @Override
    public double rastojanje(Vektor u, Vektor v) {
        if (u.velicina() != v.velicina()) {
            throw new IllegalArgumentException("Vektori nisu iste velicine");
        }
        double dist = 0;
        for (int i = 0; i < u.velicina(); i++) {
            dist += Math.abs(u.get(i) - v.get(i));
        }
        return dist;
    }
}
