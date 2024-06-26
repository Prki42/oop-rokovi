package resenje;

public abstract class Osoba {
    private String ime;
    private MuzickiZanr zanr;

    public Osoba(String ime, MuzickiZanr zanr) {
        this.zanr = zanr;
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }

    public MuzickiZanr getZanr() {
        return zanr;
    }
}
