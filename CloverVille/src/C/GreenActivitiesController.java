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

public class GreenActivitiesController {

    @FXML
    private TextField actionField;

    @FXML
    private ListView<String> actionListView;

    private final ObservableList<String> actions =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        actionListView.setItems(actions);
    }

    @FXML
    private void addAction() {
        String text = actionField.getText();
        if (text != null && !text.isEmpty()) {
            actions.add(text);
            actionField.clear();
        }
    }

    @FXML
    private void deleteAction() {
        String selected =
                actionListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            actions.remove(selected);
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




