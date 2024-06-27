import java.util.*;
import java.util.stream.Collectors;

public class FajlSistem {

    private Direktorijum koren;
    private Direktorijum trenutniDirektorijum;

    public FajlSistem() {
        this.koren = new Direktorijum("/");
        this.koren.setKoren(true);
        this.trenutniDirektorijum = this.koren;
    }

    public Direktorijum getKoren() {
        return koren;
    }

    public Direktorijum getTrenutniDirektorijum() {
        return trenutniDirektorijum;
    }

    public String ls(Comparator<Fajl> komparator) {
        return this.trenutniDirektorijum.sadrzaj(komparator);
    }

    public String pwd() {
        List<String> dirs = new LinkedList<>();
        Direktorijum temp = this.trenutniDirektorijum;
        while (temp != this.koren) {
            dirs.addFirst(temp.getNaziv());
            temp = temp.getNadDirektorijum();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        for (String dir : dirs) {
            sb.append(dir);
            sb.append("/");
        }
        return sb.toString();
    }

    public void cd(String dir) {
         if (dir.equalsIgnoreCase("..")) {
             if (this.trenutniDirektorijum == this.koren) {
                 throw new IllegalArgumentException("Ne postoji naddirektorijum");
             }
             this.trenutniDirektorijum = this.trenutniDirektorijum.getNadDirektorijum();
             return;
         }
         Fajl f = this.trenutniDirektorijum.getFajlovi().getOrDefault(dir, null);
         if (f == null) {
             throw new IllegalArgumentException("Ne postoji direktorijum sa nazivom " + dir);
         }
        if (f instanceof Direktorijum) {
            this.trenutniDirektorijum = (Direktorijum) f;
        } else {
            throw new IllegalArgumentException(dir + " nije direktorijum");
        }
    }

    private void cdRoot() {
        this.trenutniDirektorijum = this.koren;
    }

    private void buildPath(List<String> dirs) {
        cdRoot();
        for (String dir : dirs) {
            try {
                cd(dir);
            } catch (Exception _) {
                Direktorijum d = new Direktorijum(dir);
                this.trenutniDirektorijum.dodajFajl(d);
                this.trenutniDirektorijum = d;
            }
        }
    }

    public void makeFromString(String s) {
        Direktorijum temp = this.trenutniDirektorijum;

        List<String> fs = Arrays.stream(s.split("/")).collect(Collectors.toList());
        String file = fs.removeLast();
        buildPath(fs);
        Fajl newFile;
        int l = file.split(" ").length;
        if (l == 4) {
            newFile = Slika.fromString(file);
        } else if (l == 3) {
            newFile = TekstualniFajl.fromString(file);
        } else {
            throw new IllegalArgumentException("Linija [" + s + "] nije validna");
        }
        this.trenutniDirektorijum.dodajFajl(newFile);

        this.trenutniDirektorijum = temp;
    }
}
