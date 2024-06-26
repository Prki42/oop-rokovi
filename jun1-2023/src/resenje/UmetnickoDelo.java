package resenje;

public abstract class UmetnickoDelo {
    private String ID;
    private String naziv;
    private String imeAutora;
    private Kvalitet kvalitet;

    public UmetnickoDelo(String ID, String naziv, String imeAutora, Kvalitet kvalitet) {
        this.ID = ID;
        this.naziv = naziv;
        this.imeAutora = imeAutora;
        this.kvalitet = kvalitet;
    }

    public String getID() {
        return ID;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getImeAutora() {
        return imeAutora;
    }

    public Kvalitet getKvalitet() {
        return kvalitet;
    }

    public abstract int getCena();

    @Override
    public String toString() {
        return "[" + ID + "] " + kvalitet.toString() + " : " + naziv + ", " + imeAutora;
    }
}
