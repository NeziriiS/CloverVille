package C;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ClloverApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        URL mainUrl = getClass().getResource("/MainInterface.fxml");
        if (mainUrl == null) {
            throw new IllegalStateException("MainInterface.fxml not found");
        }

        FXMLLoader loader = new FXMLLoader(mainUrl);
        Scene scene = new Scene(loader.load(), 800, 600);

        URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setTitle("CloverVille");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}