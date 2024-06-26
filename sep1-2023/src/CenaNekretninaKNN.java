import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

public class CenaNekretninaKNN extends Application {
    private KNNPrediktor knn;

    private static Metrika euklidMetrika = new EuklidskaMetrika();
    private static Metrika menhetnMetrika = new MenhetnMetrika();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        HBox b1 = new HBox();
        b1.setSpacing(30);
        HBox b2 = new HBox();
        b2.setSpacing(10);
        HBox b3 = new HBox();
        b3.setSpacing(10);

        Label lblKvadratura = new Label("Kvadratura");
        Label lblStruktura = new Label("Struktura");
        Label lblSpratnost = new Label("Spratnost");
        Label lblUdaljenost = new Label("Udaljenost o centra");

        TextField tfKvadratura = new TextField();
        TextField tfStruktura = new TextField();
        TextField tfSpratnost = new TextField();
        TextField tfUdaljenost = new TextField();

        Label lblIzbor = new Label("Izbor metrike");
        ToggleGroup tgMetrika = new ToggleGroup();
        RadioButton rbEuklid = new RadioButton("Euklidska metrika");
        rbEuklid.setSelected(true);
        RadioButton rbMenhetn = new RadioButton("Menhetn metrika");
        tgMetrika.getToggles().addAll(rbEuklid, rbMenhetn);
        Label lblVrednostK = new Label("Vrednost parametra k:");
        TextField tfVrednostK = new TextField();

        Button btnPredvidi = new Button("Predvidi cenu");
        btnPredvidi.setDisable(true);
        Label lblPredvidjanje = new Label("");
        lblPredvidjanje.setTextFill(Color.BLUE);

        Button btnUcitaj = new Button("Ucitaj bazu podataka");
        Label lblGreska = new Label("");
        lblGreska.setTextFill(Color.RED);

        VBox col1 = new VBox();
        col1.setSpacing(10);
        VBox col2 = new VBox();
        col2.setSpacing(10);

        BiFunction<Label, TextField, HBox> inGen = (Label lbl, TextField tf) -> {
            HBox h = new HBox(lbl, tf);
            HBox.setHgrow(lbl, Priority.ALWAYS);
            lbl.setMaxWidth(Double.MAX_VALUE);
            h.setSpacing(10);
            return h;
        };

        HBox h1 = inGen.apply(lblKvadratura, tfKvadratura);
        HBox h2 = inGen.apply(lblStruktura, tfStruktura);
        HBox h3 = inGen.apply(lblSpratnost, tfSpratnost);
        HBox h4 = inGen.apply(lblUdaljenost, tfUdaljenost);

        col1.getChildren().addAll(h1, h2, h3, h4);
        col2.getChildren().addAll(lblIzbor, rbEuklid, rbMenhetn, lblVrednostK, tfVrednostK);

        b1.getChildren().addAll(col1, col2);

        b2.getChildren().addAll(btnPredvidi, lblPredvidjanje);
        b3.getChildren().addAll(lblGreska, btnUcitaj);
        HBox.setHgrow(lblGreska, Priority.ALWAYS);
        lblGreska.setMaxWidth(Double.MAX_VALUE);

        root.getChildren().addAll(b1, b2, b3);

        Scene scene = new Scene(root);

        Consumer<String> printErrMsg = (String errMsg) -> {
            lblGreska.setText(errMsg);
            lblPredvidjanje.setText("");
        };

        DoubleConsumer printResult = (double val) -> {
            lblGreska.setText("");
            lblPredvidjanje.setText(String.format("%.2f", val));
        };

        btnUcitaj.setOnAction(_ -> {
            VektorskiProstor vp = new VektorskiProstor();
            try {
                vp.ucitajIzFajla("src/nekretnine.txt");
            } catch (IOException e) {
                printErrMsg.accept("Neuspesno citanje iz fajlsa");
                return;
            }

            this.knn = new KNNPrediktor(vp, null, 1);

            btnUcitaj.setDisable(true);
            btnPredvidi.setDisable(false);
        });

        btnPredvidi.setOnAction(_ -> {
            if (knn == null) {
                return;
            }
            try {
                double kvadratura = Double.parseDouble(tfKvadratura.getText());
                double struktura = Double.parseDouble(tfStruktura.getText());
                double spratnost = Double.parseDouble(tfSpratnost.getText());
                double udaljenost = Double.parseDouble(tfUdaljenost.getText());
                int k = Integer.parseInt(tfVrednostK.getText());

                Vektor targetVektor = new Vektor(new double[]{kvadratura, struktura, spratnost, udaljenost});

                knn.setK(k);

                Metrika m;
                if (rbEuklid.isSelected()) {
                    m = euklidMetrika;
                } else {
                    m = menhetnMetrika;
                }

                knn.setMetrika(m);

                double pred = knn.predvidjanjeCiljnePromene(targetVektor);

                printResult.accept(pred);
            } catch (NumberFormatException _) {
                printErrMsg.accept("Nedozvoljen format brojcanih ulaza");
            } catch (IllegalArgumentException e) {
                printErrMsg.accept(e.getMessage());
            }
        });

        stage.setScene(scene);
        stage.setTitle("KNN prediktor cena nekretnina");
        stage.show();
    }
}
