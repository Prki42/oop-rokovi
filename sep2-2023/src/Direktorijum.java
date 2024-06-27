import java.util.*;

public class Direktorijum extends Fajl {

    private Direktorijum nadDirektorijum;
    private boolean koren;
    private Map<String, Fajl> fajlovi;

    public Direktorijum(String naziv) {
        super(naziv);
        this.fajlovi = new HashMap<>();
    }

    public Direktorijum getNadDirektorijum() {
        return nadDirektorijum;
    }

    public boolean isKoren() {
        return koren;
    }

    public Map<String, Fajl> getFajlovi() {
        return fajlovi;
    }

    public void setNadDirektorijum(Direktorijum nadDirektorijum) {
        this.nadDirektorijum = nadDirektorijum;
    }

    public void setKoren(boolean koren) {
        this.koren = koren;
    }

    public void setFajlovi(Map<String, Fajl> fajlovi) {
        this.fajlovi = fajlovi;
    }

    public boolean sadrzi(String naziv) {
        return this.fajlovi.containsKey(naziv);
    }

    public void dodajFajl(Fajl f) {
        if (f instanceof Direktorijum) {
            ((Direktorijum) f).setNadDirektorijum(this);
        }
        this.fajlovi.put(f.getNaziv(), f);
    }

    public String sadrzaj(Comparator<Fajl> komparator) {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getNaziv());
        sb.append("\n");

        List<Fajl> fs = this.fajlovi.values().stream().sorted(komparator).toList();

        for (Fajl f : fs) {
            sb.append("- ");
            sb.append(f.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public int velicina() {
        int x = 0;
        for (Fajl f : fajlovi.values()) {
            x += f.velicina();
        }
        return x;
    }
}
