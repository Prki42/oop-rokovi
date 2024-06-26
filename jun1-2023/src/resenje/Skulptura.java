package resenje;

public class Skulptura extends UmetnickoDelo {
    private int godinaStvaranja;
    private int tezina;

    public Skulptura(String ID, String naziv, String imeAutora, Kvalitet kvalitet, int godinaStvaranja, int tezina) {
        super(ID, naziv, imeAutora, kvalitet);
        this.godinaStvaranja = godinaStvaranja;
        this.tezina = tezina;
    }

    @Override
    public int getCena() {
        return this.getKvalitet().getCelobrojnaVred()*godinaStvaranja + tezina/100;
    }

    public int getGodinaStvaranja() {
        return godinaStvaranja;
    }

    public int getTezina() {
        return tezina;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + godinaStvaranja + ", " + tezina + "kg (" + getCena() + "$)";
    }
}
