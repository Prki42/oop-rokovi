package resenje;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Bend {
    private String naziv;
    private Muzicar pevac;
    private int vremePocetka;
    private int vremeKraja;
    private boolean strani;
    private List<Posetilac> posetioci;

    public Bend(String naziv, Muzicar pevac, int vremePocetka, int vremeKraja) {
        this.naziv = naziv;
        this.pevac = pevac;
        this.vremePocetka = vremePocetka;
        this.vremeKraja = vremeKraja;
        this.strani = false;
        this.posetioci = new LinkedList<Posetilac>();
    }

    public Bend(String naziv, Muzicar pevac, int vremePocetka, int vremeKraja, boolean strani) {
        this.naziv = naziv;
        this.pevac = pevac;
        this.vremePocetka = vremePocetka;
        this.vremeKraja = vremeKraja;
        this.strani = strani;
        this.posetioci = new LinkedList<Posetilac>();
    }

    public String getNaziv() {
        return naziv;
    }

    public Muzicar getPevac() {
        return pevac;
    }

    public int getVremePocetka() {
        return vremePocetka;
    }

    public int getVremeKraja() {
        return vremeKraja;
    }

    public List<Posetilac> getPosetioci() {
        return posetioci;
    }

    public void dodajPosetioca(Posetilac p) {
        posetioci.add(p);
    }

    @Override
    public String toString() {
        return "Bend: " + this.naziv + ", vreme: " + this.vremePocetka + "-" + this.vremeKraja + "\n" + this.pevac.toString();
    }

    public boolean isStrani() {
        return strani;
    }
}
