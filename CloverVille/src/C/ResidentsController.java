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
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResidentsController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ListView<Resident> residentListView;

    private final ObservableList<Resident> residents =
            FXCollections.observableArrayList();

    private static final Path STORAGE_PATH =
            Paths.get(System.getProperty("user.home"), "clover_residents.txt");

    @FXML
    private void initialize() {
        residentListView.setItems(residents);
        loadResidentsFromFile();
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
            saveResidentsToFile();
        }
    }

    @FXML
    private void deleteResident() {
        Resident selected = residentListView.getSelectionModel()
                .getSelectedItem();

        if (selected != null) {
            residents.remove(selected);
            saveResidentsToFile();
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


    private void loadResidentsFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }
        try (Stream<String> lines =
                     Files.lines(STORAGE_PATH, StandardCharsets.UTF_8)) {

            List<String> raw = lines
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            for (String line : raw) {
                String[] parts = line.split(";", 2);
                if (parts.length == 2) {
                    String first = parts[0];
                    String last = parts[1];
                    residents.add(new Resident(first, last));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveResidentsToFile() {
        try {
            List<String> lines = residents.stream()
                    .map(r -> r.getFirstName() + ";" + r.getLastName())
                    .collect(Collectors.toList());

            Files.write(
                    STORAGE_PATH,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
