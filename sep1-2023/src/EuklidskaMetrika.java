public class EuklidskaMetrika implements Metrika{
    @Override
    public double rastojanje(Vektor u, Vektor v) {
        if (u.velicina() != v.velicina()) {
            throw new IllegalArgumentException("Vektori nisu iste dimenzije");
        }
        double dist = 0;
        for (int i = 0; i < u.velicina(); i++) {
            dist += Math.pow(u.get(i) - v.get(i), 2);
        }
        dist = Math.sqrt(dist);
        return dist;
    }
}
