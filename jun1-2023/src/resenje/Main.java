package resenje;

import javafx.application.Application;
import javafx.geometry.Insets;
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

public class Main extends Application {
    private static AukcijskaKuca aukcijskaKuca;

    public static void main(String[] args) {
        aukcijskaKuca = new AukcijskaKuca(0);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.paddingProperty().set(new Insets(10, 10, 10, 10));
        root.setSpacing(10);

        VBox firstRow = new VBox();
        HBox secondRow = new HBox();
        VBox thirdRow = new VBox();

        secondRow.setSpacing(10);

        Button btnUcitaj = new Button("Ucitaj");
        firstRow.getChildren().add(btnUcitaj);

        VBox firstCol = new VBox();
        VBox secondCol = new VBox();

        secondCol.setSpacing(7);

        TextArea taPrikaz = new TextArea();
        taPrikaz.setEditable(false);
        firstCol.getChildren().add(taPrikaz);

        Label lblID = new Label("ID predmeta za licitaciju");
        TextField tfID = new TextField();
        Label lblCena = new Label("Ponudjena cena");
        TextField tfCena = new TextField();
        Button btnLicitiraj = new Button("Licitiraj");
        Label lblStatus = new Label("");

        secondCol.getChildren().addAll(lblID, tfID, lblCena, tfCena, btnLicitiraj, lblStatus);

        secondRow.getChildren().addAll(firstCol, secondCol);

        HBox hbZarada = new HBox();
        Label lblZaradaKuce = new Label("Zarada kuce");
        TextField tfZaradaKuce = new TextField();
        tfZaradaKuce.setEditable(false);
        hbZarada.getChildren().addAll(lblZaradaKuce, tfZaradaKuce);
        hbZarada.setSpacing(7);

        thirdRow.getChildren().addAll(hbZarada);

        root.getChildren().addAll(firstRow, secondRow, thirdRow);

        Scene scene = new Scene(root);

        Runnable updatePrikaz = () -> {
            taPrikaz.clear();
            taPrikaz.appendText(aukcijskaKuca.toString());
        };

        btnUcitaj.setOnAction(_ -> {
            try {
                aukcijskaKuca.ucitajUmetnickaDelaIzFajla("src/resenje/za_prodaju.txt");
                btnUcitaj.setDisable(true);
                updatePrikaz.run();
            } catch (IOException e) {
                lblStatus.setTextFill(Color.RED);
                lblStatus.setText("Unos neuspesan.");
            }
        });

        Runnable neuspesnaLicitacija = () -> {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Neuspesna licitacija!");
        };

        btnLicitiraj.setOnAction(_ -> {
            String ID = tfID.getText();
            if (ID.isBlank()) {
                neuspesnaLicitacija.run();
                return;
            }
            int ponudjenaCena;
            try {
                ponudjenaCena = Integer.parseInt(tfCena.getText());
            } catch (NumberFormatException e) {
                neuspesnaLicitacija.run();
                return;
            }
            if (ponudjenaCena < 0) {
                neuspesnaLicitacija.run();
                return;
            }
            if (!aukcijskaKuca.licitacija(ID, ponudjenaCena)) {
                neuspesnaLicitacija.run();
                return;
            }

            lblStatus.setTextFill(Color.BLUE);
            lblStatus.setText("Licitacija uspesna!");

            updatePrikaz.run();

            tfZaradaKuce.setText(Integer.toString(aukcijskaKuca.getZarada()));
        });

        stage.setScene(scene);
        stage.setTitle("Aukcijska kuca");
        stage.show();
    }
}
