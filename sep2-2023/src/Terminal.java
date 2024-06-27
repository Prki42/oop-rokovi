import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Terminal extends Application {
    private FajlSistem fs;

    public static void main(String[] args) {
        launch(args);
    }

    private void ucitajIzFajla(String path) throws IOException {
        fs = new FajlSistem();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            fs.makeFromString(line);
        }
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        HBox r1 = new HBox();
        r1.setSpacing(10);
        VBox r2 = new VBox();
        r2.setSpacing(10);

        Label lblTerminal = new Label("Terminal:");
        TextField tfPrompt = new TextField();
        Button btnRun = new Button("Izvrsi");
        btnRun.setDisable(true);

        r1.getChildren().addAll(lblTerminal, tfPrompt, btnRun);

        ToggleGroup tgSort = new ToggleGroup();
        RadioButton rbNaziv = new RadioButton("Sort po nazivu");
        RadioButton rbVelicina = new RadioButton("Sort po velicini");
        rbNaziv.setToggleGroup(tgSort);
        rbVelicina.setToggleGroup(tgSort);
        rbNaziv.setSelected(true);

        r2.getChildren().addAll(rbNaziv, rbVelicina);

        TextArea taPrikaz = new TextArea();
        taPrikaz.setEditable(false);

        root.getChildren().addAll(r1, r2, taPrikaz);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Fajl sistem");

        Consumer<String> prikazi = (String s) -> {
            taPrikaz.clear();
            taPrikaz.setText(s);
        };

        try {
            ucitajIzFajla("src/fajlovi.txt");
            prikazi.accept(fs.getTrenutniDirektorijum().sadrzaj(Fajl.cmpNaziv));
            btnRun.setDisable(false);
        } catch (IOException e) {
            prikazi.accept("Nije moguce otvoriti fajl");
        } catch (Exception e) {
            prikazi.accept(e.getMessage());
        }

        btnRun.setOnAction(_ -> {
            String[] cmd = Arrays.stream(tfPrompt.getText().split(" ")).map(String::trim).toArray(String[]::new);
            if (cmd.length == 0) {
                return;
            }
            Comparator<Fajl> cmp;
            if (rbNaziv.isSelected()) {
                cmp = Fajl.cmpNaziv;
            } else {
                cmp = Fajl.cmpVelicina;
            }
            if (cmd[0].equals("ls")) {
                if (cmd.length >= 2) {
                    prikazi.accept("ls ne prihvata dodatne parametre");
                    return;
                }
                prikazi.accept(fs.ls(cmp));
            } else if (cmd[0].equals("cd")) {
                if (cmd.length != 2) {
                    prikazi.accept("cmd zahteva jos jedan argument");
                    return;
                }
                try {
                    fs.cd(cmd[1]);
                    prikazi.accept("Tekuci direktorijum: " + fs.getTrenutniDirektorijum().getNaziv());
                } catch (Exception e) {
                    prikazi.accept("Nije moguce prebaciti se u direktorijum " + cmd[1]);
                }
            } else if (cmd[0].equals("pwd")) {
                if (cmd.length >= 2) {
                    prikazi.accept("pwd ne prihvata dodatne parametre");
                    return;
                }
                prikazi.accept(fs.pwd());
            } else {
                prikazi.accept("Komanda " + cmd[0] + " ne postoji");
            }
        });

        stage.show();
    }
}