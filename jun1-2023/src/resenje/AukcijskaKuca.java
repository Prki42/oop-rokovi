package resenje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AukcijskaKuca {
    private List<UmetnickoDelo> umetnickaDelaNaProdaju;
    private int zarada;

    public AukcijskaKuca(int zarada) {
        this.zarada = zarada;
        this.umetnickaDelaNaProdaju = new LinkedList<UmetnickoDelo>();
    }

    public AukcijskaKuca(List<UmetnickoDelo> umetnickaDelaNaProdaju, int zarada) {
        this.umetnickaDelaNaProdaju = umetnickaDelaNaProdaju;
        this.zarada = zarada;
    }

    public List<UmetnickoDelo> getUmetnickaDelaNaProdaju() {
        return umetnickaDelaNaProdaju;
    }

    public int getZarada() {
        return zarada;
    }

    public void dodajUmetnickoDelo(UmetnickoDelo u) {
        this.umetnickaDelaNaProdaju.add(u);
    }

    public void ucitajUmetnickaDelaIzFajla(String filePath) throws IOException {
        Path p = Paths.get(filePath);
        List<String> lines = Files.readAllLines(p);
        for (String line : lines) {
            UmetnickoDelo u;
            String[] splitted = line.split(", ");
            if (splitted.length == 7) {
                u = new Knjiga(
                        splitted[0],
                        splitted[1],
                        splitted[2],
                        Kvalitet.createFromInt(Integer.parseInt(splitted[3])),
                        splitted[4],
                        Integer.parseInt(splitted[5]),
                        Integer.parseInt(splitted[6])
                );
            } else {
                u = new Skulptura(
                        splitted[0],
                        splitted[1],
                        splitted[2],
                        Kvalitet.createFromInt(Integer.parseInt(splitted[3])),
                        Integer.parseInt(splitted[4]),
                        Integer.parseInt(splitted[5])
                );
            }
            dodajUmetnickoDelo(u);
        }
    }

    public boolean licitacija(String ID, int ponudjenaCena) {
        UmetnickoDelo u = this.umetnickaDelaNaProdaju.stream().filter(ud -> ud.getID().equals(ID)).findFirst().orElse(null);
        if (u == null) {
            return false;
        }
        int cena = u.getCena();
        if (cena <= ponudjenaCena) {
            this.zarada += ponudjenaCena - cena;
            this.umetnickaDelaNaProdaju.remove(u);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.umetnickaDelaNaProdaju.sort(new KomparatorUmetnickihDela());
        for (UmetnickoDelo u : this.umetnickaDelaNaProdaju) {
            sb.append(u.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
