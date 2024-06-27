import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class ArheoloskoNalaziste {
    private List<Artefakt> artefakti;

    public ArheoloskoNalaziste(List<Artefakt> artefakti) {
        this.artefakti = artefakti;
    }

    public List<Artefakt> getArtefakti() {
        return artefakti;
    }

    public Artefakt iskopavanje(Arheolog arheolog) {
        int n = artefakti.size();
        if (n == 0) {
            return null;
        }
        int ind = (new Random()).nextInt(n);
        Artefakt a = artefakti.get(ind);
        artefakti.remove(ind);
        if (a.iskopajArtefakt(arheolog.getKvalifikacija())) {
            return a;
        } else {
            arheolog.oduzmiLicencu();
            return null;
        }
    }

    public void ucitajIzFajla(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            if (line.isEmpty()) continue;
            artefakti.add(Artefakt.parse(line));
        }
    }
}
