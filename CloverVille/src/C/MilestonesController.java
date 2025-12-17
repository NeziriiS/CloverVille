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

public class MilestonesController {

    @FXML
    private TextField milestoneField;

    @FXML
    private ListView<Milestone> milestoneListView;

    private final ObservableList<Milestone> milestones =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        milestoneListView.setItems(milestones);
    }

    @FXML
    private void addMilestone() {
        String title = milestoneField.getText();

        if (title != null && !title.isEmpty()) {
            milestones.add(new Milestone(title));
            milestoneField.clear();
        }
    }

    @FXML
    private void deleteMilestone() {
        Milestone selected =
                milestoneListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            milestones.remove(selected);
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



