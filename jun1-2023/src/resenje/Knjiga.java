package resenje;

public class Knjiga extends UmetnickoDelo {
    private String izdavac;
    private int godinaIzdanja;
    private int brojStrana;

    public Knjiga(String ID, String naziv, String imeAutora, Kvalitet kvalitet, String izdavac, int godinaIzdanja, int brojStrana) {
        super(ID, naziv, imeAutora, kvalitet);
        this.izdavac = izdavac;
        this.godinaIzdanja = godinaIzdanja;
        this.brojStrana = brojStrana;
    }

    @Override
    public int getCena() {
        return brojStrana * this.getKvalitet().getCelobrojnaVred();
    }

    public String getIzdavac() {
        return izdavac;
    }

    public int getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public int getBrojStrana() {
        return brojStrana;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + izdavac + " " + godinaIzdanja + ", " + brojStrana + " strana (" + getCena() + "$)";
    }
}
