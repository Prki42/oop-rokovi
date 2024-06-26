import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VektorskiProstor {
    private ArrayList<String> naziviKoordinata;
    private ArrayList<Vektor> vektori;

    public VektorskiProstor() {
    }

    public VektorskiProstor(ArrayList<String> naziviKoordinata, ArrayList<Vektor> vektori) {
        this.naziviKoordinata = naziviKoordinata;
        this.vektori = vektori;
    }

    public ArrayList<String> getNaziviKoordinata() {
        return naziviKoordinata;
    }

    public ArrayList<Vektor> getVektori() {
        return vektori;
    }

    public void ucitajIzFajla(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        this.naziviKoordinata = new ArrayList<>(List.of(lines.getFirst().split(" ")));
        lines.removeFirst();
        this.vektori = new ArrayList<>();
        lines.forEach(line -> {
            vektori.add(new Vektor(
                    (ArrayList<Double>) Stream.of(line.split(" ")).map(Double::parseDouble).collect(Collectors.toCollection(ArrayList::new))
            ));
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String k : naziviKoordinata) {
            sb.append(k).append(" ");
        }
        sb.append("\n");
        for (Vektor v : vektori) {
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }
}
