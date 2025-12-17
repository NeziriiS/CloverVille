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

public class CommunalTasksController {

    @FXML
    private TextField taskField;

    @FXML
    private ListView<CommunalTask> taskListView;

    private final ObservableList<CommunalTask> tasks =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        taskListView.setItems(tasks);
    }

    @FXML
    private void addTask() {
        String name = taskField.getText();

        if (name != null && !name.isEmpty()) {
            tasks.add(new CommunalTask(name));
            taskField.clear();
        }
    }

    @FXML
    private void deleteTask() {
        CommunalTask selected =
                taskListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            tasks.remove(selected);
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
