import java.util.Comparator;

public abstract class Fajl {
    private String naziv;
    public static Comparator<Fajl> cmpNaziv = Comparator.comparingInt(Fajl::velicina);
    public static Comparator<Fajl> cmpVelicina = Comparator.comparing(Fajl::getNaziv);

    public Fajl(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public abstract int velicina();

    public static String calcVelicinaStr(int velicina) {
        int step = 1024;
        if (velicina >= step) {
            velicina = velicina / step;
            if (velicina >= step) {
                velicina = velicina / step;
                if (velicina >= step) {
                    velicina = velicina / step;
                    return velicina + "GB";
                } else {
                    return velicina + "MB";
                }
            } else {
                return velicina + "KB";
            }
        }
        return velicina + "B";
    }

    @Override
    public String toString() {
        return naziv + " " + calcVelicinaStr(velicina());
    }
}
