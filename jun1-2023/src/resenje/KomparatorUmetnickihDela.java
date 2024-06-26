package resenje;

import java.util.Comparator;

public class KomparatorUmetnickihDela implements Comparator<UmetnickoDelo> {
    @Override
    public int compare(UmetnickoDelo u1, UmetnickoDelo u2) {
        if (u1 instanceof Knjiga && u2 instanceof Skulptura) {
            return -1;
        }
        if (u2 instanceof Knjiga && u1 instanceof Skulptura) {
            return 1;
        }
        int cmp;
        if ((cmp = Integer.compare(u2.getKvalitet().getCelobrojnaVred(), u1.getKvalitet().getCelobrojnaVred())) != 0) {
            return cmp;
        }
        if (u1 instanceof Knjiga) {
            return Integer.compare(((Knjiga) u1).getGodinaIzdanja(), ((Knjiga) u2).getGodinaIzdanja());
        } else {
            return Integer.compare(((Skulptura) u1).getGodinaStvaranja(), ((Skulptura) u2).getGodinaStvaranja());
        }
    }
}
