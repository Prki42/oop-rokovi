import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Main extends Application {
    private ArheoloskoNalaziste nalaziste;
    private Arheolog trenutniArheolog;
    private Map<Arheolog, List<Artefakt>> zbirkaIskopina = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        HBox r1 = new HBox();
        r1.setSpacing(10);
        TextArea taPrikaz = new TextArea();
        taPrikaz.setEditable(false);
        HBox r2 = new HBox();
        r2.setSpacing(10);
        Label lblInfoBox = new Label("");

        root.getChildren().addAll(r1, lblInfoBox, taPrikaz, r2);

        VBox c1 = new VBox();
        c1.setSpacing(10);
        VBox c2 = new VBox();
        c2.setSpacing(10);
        VBox c3 = new VBox();
        c3.setSpacing(10);
        c3.setAlignment(Pos.BOTTOM_CENTER);

        Label lblImePrezime = new Label("Ime i prezime:");
        TextField tfImePrezime = new TextField();

        Label lblKvalifikacija = new Label("Kvalifikacija (1-10):");
        TextField tfKvalifikacija = new TextField();

        Button btnAngazuj = new Button("Angazuj arheologa");

        c1.getChildren().addAll(lblImePrezime, tfImePrezime);
        c2.getChildren().addAll(lblKvalifikacija, tfKvalifikacija);
        c3.getChildren().addAll(btnAngazuj);

        r1.getChildren().addAll(c1, c2, c3);

        Button btnOtkrijNalaziste = new Button("Otkrij nalaziste");
        Button btnIskopaj = new Button("Iskopaj artefakt");
        Button btnIspisiZbirku = new Button("Ispisi izbirku iskopina");

        r2.getChildren().addAll(btnOtkrijNalaziste, btnIskopaj, btnIspisiZbirku);

        Scene scene = new Scene(root);

        stage.setTitle("Arheolosko nalaziste");
        stage.setScene(scene);

        Consumer<String> showErr = (String err) -> {
            taPrikaz.clear();
            lblInfoBox.setTextFill(Color.RED);
            lblInfoBox.setText(err);
        };

        btnOtkrijNalaziste.setOnAction(_ -> {
            nalaziste = new ArheoloskoNalaziste(new LinkedList<>());
            try {
                nalaziste.ucitajIzFajla("src/nalaziste.txt");
            } catch (IOException _) {
                showErr.accept("Neuspelo citanje iz fajla");
            } catch (IllegalArgumentException e) {
                showErr.accept(e.getMessage());
            }

            btnOtkrijNalaziste.setDisable(true);
        });

        btnAngazuj.setOnAction(_ -> {
            String imePrezime = tfImePrezime.getText();
            String kvalifikacijaStr = tfKvalifikacija.getText();

            if (imePrezime.isEmpty()) {
                showErr.accept("Ime mora biti uneto");
                return;
            }

            Arheolog arh = zbirkaIskopina.keySet()
                    .stream()
                    .filter(a -> a.getImePrezime().equals(imePrezime))
                    .findFirst()
                    .orElse(null);

            if (arh == null) {
                int kvalifikacija;
                if (kvalifikacijaStr.isEmpty()) {
                    showErr.accept("Kvalifikacija mora biti uneta");
                    return;
                }
                try {
                    kvalifikacija = Integer.parseInt(kvalifikacijaStr);
                    arh = new Arheolog(imePrezime, kvalifikacija);
                } catch (NumberFormatException _) {
                    showErr.accept("Kvalifkacija mora biti broj izmedju 1 i 10");
                    return;
                } catch (IllegalArgumentException e) {
                    showErr.accept(e.getMessage());
                    return;
                }

                this.trenutniArheolog = arh;
                this.zbirkaIskopina.put(arh, new LinkedList<>());

                lblInfoBox.setTextFill(Color.GREEN);
                lblInfoBox.setText("Angazovan je novi arheolog " + imePrezime + "koji je linceniran za iskopavanja");
                return;
            }

            if (arh.isLicenciran()) {
                this.trenutniArheolog = arh;
                lblInfoBox.setTextFill(Color.BLUE);
                lblInfoBox.setText("Postojeci arheolog " + arh.getImePrezime() + " je licenciran za iskopavanja");
            } else {
                this.trenutniArheolog = null;
                lblInfoBox.setTextFill(Color.RED);
                lblInfoBox.setText("Postojeci arheolog " + arh.getImePrezime() + " nije licenciran za iskopavanja");
            }
        });

        btnIskopaj.setOnAction(_ -> {
            if (this.trenutniArheolog == null) {
                return;
            }
            Artefakt artefakt = nalaziste.iskopavanje(this.trenutniArheolog);
            if (artefakt == null) {
                taPrikaz.appendText("Neuspesno iskopavanja. Artefakt je unisten prilikom iskopavanja\n");
                return;
            }
            taPrikaz.appendText("Iskopan artefakt: " + artefakt + "\n");
            List<Artefakt> iskopani = this.zbirkaIskopina.getOrDefault(this.trenutniArheolog, null);
            if (iskopani == null) {
                iskopani = new LinkedList<>();
                this.zbirkaIskopina.put(trenutniArheolog, iskopani);
            }
            iskopani.add(artefakt);
        });

        btnIspisiZbirku.setOnAction(_ -> {
            taPrikaz.clear();
            this.zbirkaIskopina.keySet().forEach(arh -> {
                taPrikaz.appendText(arh.getImePrezime() + "\n");
                for (Artefakt art : this.zbirkaIskopina.get(arh)) {
                    taPrikaz.appendText(art.toString() + "\n");
                }
                taPrikaz.appendText("\n");
            });
        });

        stage.show();
    }
}
