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

public class ResidentsController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ListView<Resident> residentListView;

    private final ObservableList<Resident> residents =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        residentListView.setItems(residents);
    }

    @FXML
    private void addResident() {
        String first = firstNameField.getText();
        String last = lastNameField.getText();

        if (first != null && !first.isEmpty()
                && last != null && !last.isEmpty()) {
            residents.add(new Resident(first, last));
            firstNameField.clear();
            lastNameField.clear();
        }
    }

    @FXML
    private void deleteResident() {
        Resident selected = residentListView.getSelectionModel()
                .getSelectedItem();

        if (selected != null) {
            residents.remove(selected);
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



