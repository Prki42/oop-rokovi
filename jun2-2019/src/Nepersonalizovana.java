public class Nepersonalizovana extends BusPlus {
    private int kredit;
    private boolean ocitana;

    static int cenaVoznje = 90;

    public Nepersonalizovana(int id, int zona, int kredit, boolean ocitana) {
        super(id, zona);
        this.kredit = kredit;
        this.ocitana = ocitana;
    }

    public int getKredit() {
        return kredit;
    }

    public boolean isOcitana() {
        return ocitana;
    }

    public static int getCenaVoznje() {
        return cenaVoznje;
    }

    public boolean nedovoljnoKredita() {
        return cenaVoznje > kredit;
    }

    @Override
    public String toString() {
        return "[NP] " + super.toString() + ", kredit: " + kredit + " | "
                + (nedovoljnoKredita() ? "nedovoljno kredita" : (ocitana ? "ocitana" : "nije ocitana"));
    }

    public boolean ocitajKartu() {
        if (ocitana || nedovoljnoKredita()) return false;
        this.kredit -= cenaVoznje;
        this.ocitana = true;
        return true;
    }
}
