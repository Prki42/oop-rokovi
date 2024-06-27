public class TekstualniFajl extends Fajl {
    private int brojKaraktera;
    private Enkodiranje enkodiranje;

    public TekstualniFajl(String naziv, int brojKaraktera, Enkodiranje enkodiranje) {
        super(naziv);
        this.brojKaraktera = brojKaraktera;
        this.enkodiranje = enkodiranje;
    }

    public int getBrojKaraktera() {
        return brojKaraktera;
    }

    public Enkodiranje getEnkodiranje() {
        return enkodiranje;
    }

    public void setBrojKaraktera(int brojKaraktera) {
        this.brojKaraktera = brojKaraktera;
    }

    public void setEnkodiranje(Enkodiranje enkodiranje) {
        this.enkodiranje = enkodiranje;
    }

    @Override
    public int velicina() {
        return brojKaraktera * enkodiranje.velicinaEnkodiranjaKaraktera();
    }

    public static TekstualniFajl fromString(String s) {
        String[] params = s.split(" ");
        String naziv = params[0];
        int brojKaraktera;
        try {
            brojKaraktera = Integer.parseInt(params[1]);
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException("Broj karaktera nije brojcana vrednost");
        }
        Enkodiranje e;
        switch (params[2]) {
            case "ASCII" -> e = Enkodiranje.ASCII;
            case "UTF8" -> e = Enkodiranje.UTF8;
            case "UTF16" -> e = Enkodiranje.UTF16;
            default -> throw new IllegalArgumentException("Enkodiranje '"+params[2]+"' nije postojece");
        }
        return new TekstualniFajl(naziv, brojKaraktera, e);
    }
}
