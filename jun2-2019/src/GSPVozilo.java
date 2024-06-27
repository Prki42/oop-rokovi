import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class GSPVozilo {
    private Map<Integer, BusPlus> kartice;
    private List<Integer> nevalidne;

    private static Random random = new Random();
    private static int noviId = 1000;

    public GSPVozilo() {
        kartice = new HashMap<Integer, BusPlus>();
        nevalidne = new ArrayList<Integer>();
    }

    public Map<Integer, BusPlus> getKartice() {
        return kartice;
    }

    public boolean putniciUVozilu(String putanja) {
        try {
            Files.lines(Paths.get(putanja)).forEach(line -> {
                BusPlus b = BusPlus.parse(line);
                this.kartice.put(b.getId(), b);
            });
        } catch (IOException e) {
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public BusPlus noviPutnik(int vrstaKarte) {
        int id = noviId;
        noviId++;
        if (vrstaKarte == 1) {
            Kategorija kategorija = Kategorija.izBroja(random.nextInt(4));
            return new Personalizovana(id, vrstaKarte, kategorija, random.nextBoolean());
        } else {
            Nepersonalizovana ret = new Nepersonalizovana(id, vrstaKarte, random.nextInt(2*Nepersonalizovana.cenaVoznje+1), false);
            ret.ocitajKartu();
            return ret;
        }
    }

    public String kontrola() {
        return kartice.values().stream()
                .map(b -> {
                    String prefix = "+";
                    if (b instanceof Personalizovana) {
                        if (!((Personalizovana) b).isImaDopunu()) prefix = "-";
                    } else if (b instanceof  Nepersonalizovana) {
                        if (!((Nepersonalizovana) b).isOcitana()) prefix = "-";
                    }
                    return prefix + " " + b.toString();
                })
                .collect(Collectors.joining("\n"));
    }

    public boolean izbaciPutnike() {
        if (nevalidne.isEmpty()) return false;
        nevalidne.forEach(i -> {
            kartice.remove(i);
        });
        return true;
    }
}
