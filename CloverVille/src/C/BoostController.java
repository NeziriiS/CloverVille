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

public class BoostController {

    @FXML
    private TextField boostField;

    @FXML
    private ListView<Boost> boostListView;

    private final ObservableList<Boost> boosts =
            FXCollections.observableArrayList();

    private static final Path STORAGE_PATH =
            Paths.get(System.getProperty("user.home"), "clover_boosts.txt");

    @FXML
    private void initialize() {
        boostListView.setItems(boosts);
        loadBoostsFromFile();
    }

    @FXML
    private void addBoost() {
        String name = boostField.getText();

        if (name != null && !name.isEmpty()) {
            boosts.add(new Boost(name));
            boostField.clear();
            saveBoostsToFile();
        }
    }

    @FXML
    private void deleteBoost() {
        Boost selected =
                boostListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            boosts.remove(selected);
            saveBoostsToFile();
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

    private void loadBoostsFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }
        try (Stream<String> lines =
                     Files.lines(STORAGE_PATH, StandardCharsets.UTF_8)) {

            List<String> names = lines
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            names.forEach(n -> boosts.add(new Boost(n)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBoostsToFile() {
        try {
            List<String> lines = boosts.stream()
                    .map(Boost::getName)
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
