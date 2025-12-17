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

public class RegisterActivitiesController {

    @FXML
    private TextField activityField;

    @FXML
    private ListView<String> activityListView;

    private final ObservableList<String> activities =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        activityListView.setItems(activities);
    }

    @FXML
    private void backToMain(ActionEvent event) {
        switchScene(event, "/MainInterface.fxml");
    }

    @FXML
    public void addActivity(ActionEvent event) {
        String text = activityField.getText();
        if (text != null && !text.isEmpty()) {
            activities.add(text);
            activityField.clear();
        }
    }

    @FXML
    public void deleteActivity(ActionEvent event) {
        String selected =
                activityListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            activities.remove(selected);
        }
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

