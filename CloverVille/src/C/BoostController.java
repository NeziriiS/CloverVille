package C;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BoostController {

    @FXML
    private TextField boostField;

    @FXML
    private ListView<Boost> boostListView;

    private final ObservableList<Boost> boosts =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        boostListView.setItems(boosts);
    }

    @FXML
    private void addBoost() {
        String name = boostField.getText();

        if (name != null && !name.isEmpty()) {
            boosts.add(new Boost(name));
            boostField.clear();
        }
    }

    @FXML
    private void deleteBoost() {
        Boost selected =
                boostListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            boosts.remove(selected);
        }
    }

    @FXML
    private void goBackToMain(ActionEvent event) {
        switchScene(event, "/MainInterface.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                throw new IllegalStateException("FXML not found: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), 800, 600);

            URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
