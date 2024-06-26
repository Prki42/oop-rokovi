package resenje;

public class Muzicar extends Osoba{

    public Muzicar(String ime, MuzickiZanr zanr) {
        super(ime, zanr);
    }

    @Override
    public String toString() {
        return "Ja sam muzicar " + this.getIme() + " i sviram u " + this.getZanr() + " bendu";
    }
}
