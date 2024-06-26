package resenje;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Koncert extends Application {
    private List<Bend> bendovi;
    private static boolean bendoviUcitani = false;

//    private void ispisiBendove() {
//        for (Bend bend : this.bendovi) {
//            taPrikaz.appendText(bend.toString());
//            taPrikaz.appendText("\n\n");
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();

        Insets defaultInsets = new Insets(10, 10, 10, 10);
        root.setSpacing(10);

        root.setPadding(defaultInsets);

        VBox firstColumn = new VBox();
        VBox secondColumn = new VBox();

        firstColumn.setSpacing(5);
        secondColumn.setSpacing(5);

        Button btnUcitajBendove = new Button("Ucitaj bendove");
        Label lblImePosetioca = new Label("Ime posetioca");
        TextField tfImePosetioca = new TextField();
        Label lblZeljeniZanr = new Label("Zeljeni zanr");
        ToggleGroup tgZeljeniZanr = new ToggleGroup();
        RadioButton rbPop = new RadioButton("POP");
        RadioButton rbRok = new RadioButton("ROK");
        RadioButton rbRep = new RadioButton("REP");
        rbPop.setToggleGroup(tgZeljeniZanr);
        rbRok.setToggleGroup(tgZeljeniZanr);
        rbRok.setSelected(true);
        rbRep.setToggleGroup(tgZeljeniZanr);
        Label lblSlobodnoVreme = new Label("Slobodno vreme");
        TextField tfSlobodnoVreme = new TextField();
        Label lblZnaEngleski =  new Label("Zna engleski");
        ToggleGroup tgZnaEngleski = new ToggleGroup();
        RadioButton rbDa = new RadioButton("DA");
        RadioButton rbNe = new RadioButton("NE");
        rbDa.setToggleGroup(tgZnaEngleski);
        rbDa.setSelected(true);
        rbNe.setToggleGroup(tgZnaEngleski);
        Button btnDodajPosetioca = new Button("Dodaj posetioca");
        Button btnOcisti = new Button("Ocisti");

        firstColumn.getChildren().addAll(btnUcitajBendove, lblImePosetioca, tfImePosetioca, lblZeljeniZanr, rbPop, rbRok, rbRep, lblSlobodnoVreme, tfSlobodnoVreme, lblZnaEngleski, rbDa, rbNe, btnDodajPosetioca, btnOcisti);

        TextField tfBend = new TextField();
        Button btnIspisiBendIPosetioce = new Button("Ispisi bend i posetioce");
        Label lblKriterijum = new Label("Odaberite kriterijum za sortiranje");
        ToggleGroup tgKriterijum = new ToggleGroup();
        RadioButton rbBroj = new RadioButton("broj posetilaca (opadajuce)");
        RadioButton rbVreme = new RadioButton("vreme pocetka (rastuce)");
        rbVreme.setToggleGroup(tgKriterijum);
        rbBroj.setToggleGroup(tgKriterijum);
        Button btnPrikaziSortirano = new Button("Prikazi sortirano");
        TextArea taPrikaz = new TextArea();

        secondColumn.getChildren().addAll(tfBend, btnIspisiBendIPosetioce, lblKriterijum, rbBroj, rbVreme, btnPrikaziSortirano, taPrikaz);

        root.getChildren().addAll(firstColumn, secondColumn);

        // ----- DUGMICI ------

        Runnable ispisiBendove = () -> {
            for (Bend bend : this.bendovi) {
                taPrikaz.appendText(bend.toString());
                taPrikaz.appendText("\n\n");
            }
        };
        btnUcitajBendove.setOnAction(e -> {
            if (Koncert.bendoviUcitani) {
                return;
            }

            this.bendovi = new LinkedList<Bend>();

            Path podaciPath = Paths.get("src/resenje/podaci.txt");
            try {
                List<String> lines = Files.readAllLines(podaciPath);
                for (String line : lines) {
                    String[] words = line.split(", ");
                    MuzickiZanr zanr;
                    try {
                        Integer zanrInt = Integer.valueOf(words[4]);
                        zanr = MuzickiZanr.odrediZanr(zanrInt);
                    } catch (NumberFormatException _) {
                        zanr = MuzickiZanr.odrediZanr(words[4]);
                    }
                    Muzicar pevac = new Muzicar(words[1], zanr);
                    boolean straniBend = false;
                    if (words.length > 5) {
                        if (words[5].equals("strani")) {
                            straniBend = true;
                        }
                    }
                    Bend bend = new Bend(words[0], pevac, Integer.parseInt(words[2]), Integer.parseInt(words[3]), straniBend);
                    this.bendovi.add(bend);
                }
                ispisiBendove.run();
                Koncert.bendoviUcitani = true;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        btnDodajPosetioca.setOnAction(_ -> {
            String ime = tfImePosetioca.getText();
            String vremeStr = tfSlobodnoVreme.getText();
            if (ime.isBlank() || vremeStr.isBlank()) {
                return;
            }

            int pocetak, kraj;
            String[] vremeSplit = vremeStr.split("-");
            if (vremeSplit.length != 2) {
                return;
            }
            try {
                pocetak = Integer.parseInt(vremeSplit[0]);
                kraj = Integer.parseInt(vremeSplit[1]);
            } catch (NumberFormatException _) {
                return;
            }

            if ((pocetak < 0 || pocetak > 24) || (kraj < 0 || kraj > 24) || (pocetak >= kraj)) {
                return;
            }

            MuzickiZanr zanr;
            if (rbPop.isSelected()) {
                zanr = MuzickiZanr.POP;
            } else if (rbRok.isSelected()) {
                zanr = MuzickiZanr.ROK;
            } else if (rbRep.isSelected()) {
                zanr = MuzickiZanr.REP;
            } else {
                return;
            }

            Posetilac p;
            if (rbDa.isSelected()) {
                p = new PosetilacKojiZnaEngleski(ime, zanr, pocetak, kraj);
            } else {
                p = new Posetilac(ime, zanr, pocetak, kraj);
            }

            for (Bend bend : this.bendovi) {
                if (!bend.getPevac().getZanr().equals(p.getZanr())) {
                    continue;
                }
                if (!rbDa.isSelected() && bend.isStrani()) {
                    continue;
                }
                if (bend.getVremePocetka() <= p.getPocetakSlobodnogVremena() && bend.getVremeKraja() >= p.getKrajSlobodnogVremena()) {
                    bend.dodajPosetioca(p);
                    taPrikaz.clear();
                    taPrikaz.appendText("Posetilac " + p.getIme() + " ce slusati " + bend.getNaziv() + "\n");
                    break;
                }
            }
        });

        btnIspisiBendIPosetioce.setOnAction(e -> {
            if (!Koncert.bendoviUcitani) {
                return;
            }
            String naziv = tfBend.getText();
            if (naziv.isBlank()) {
                return;
            }
            taPrikaz.clear();
            for (Bend bend : this.bendovi) {
                if (!bend.getNaziv().trim().equalsIgnoreCase(naziv.trim())) {
                    continue;
                }
                taPrikaz.appendText(bend.toString());
                taPrikaz.appendText("\nPosetioci:\n");
                for (Posetilac posetilac : bend.getPosetioci()) {
                    taPrikaz.appendText("\t");
                    taPrikaz.appendText(posetilac.toString());
                    taPrikaz.appendText("\n");
                }
            }

        });

        btnOcisti.setOnAction(e -> {
            taPrikaz.clear();
        });

        btnPrikaziSortirano.setOnAction(e -> {
            if (!Koncert.bendoviUcitani) {
                return;
            }
            taPrikaz.clear();
            if (rbBroj.isSelected()) {
                bendovi.sort((bend, t1) -> Integer.compare(t1.getPosetioci().size(), bend.getPosetioci().size()));
            } else {
                bendovi.sort(Comparator.comparingInt(Bend::getVremePocetka));
            }
            ispisiBendove.run();

        });
        // --------------------

        Scene scene = new Scene(root, 700, 450);

        stage.setScene(scene);
        stage.setTitle("Koncert");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
