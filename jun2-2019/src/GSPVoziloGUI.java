import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GSPVoziloGUI extends Application {
    public static void main(String[] args) {
        gsp = new GSPVozilo();
        launch(args);
    }

    static private GSPVozilo gsp;
    static private List<BusPlus> kartice = null;

    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();

        VBox c1 = new VBox();
        VBox c2 = new VBox();

        root.getChildren().addAll(c1, c2);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gradski prevoz");
        stage.show();

    }
}
