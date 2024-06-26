package resenje;

public class Posetilac extends Osoba{
    private int pocetakSlobodnogVremena;
    private int krajSlobodnogVremena;

    public Posetilac(String ime, MuzickiZanr zanr, int pocetakSlobodnogVremena, int krajSlobodnogVremena) {
        super(ime, zanr);
        this.pocetakSlobodnogVremena = pocetakSlobodnogVremena;
        this.krajSlobodnogVremena = krajSlobodnogVremena;
    }

    @Override
    public String toString() {
        return "Ja sam posetilac " + this.getIme() + " i volim da slusam " + this.getZanr() + " muziku";
    }

    public int getPocetakSlobodnogVremena() {
        return pocetakSlobodnogVremena;
    }

    public int getKrajSlobodnogVremena() {
        return krajSlobodnogVremena;
    }
}
