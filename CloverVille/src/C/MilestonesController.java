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
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MilestonesController {

    @FXML
    private TextField milestoneField;

    @FXML
    private ListView<Milestone> milestoneListView;

    private final ObservableList<Milestone> milestones =
            FXCollections.observableArrayList();

    // fajl na disku
    private static final Path STORAGE_PATH =
            Paths.get(System.getProperty("user.home"), "clover_milestones.txt");

    // URL backend servera – PROMENI po potrebi
    private static final String BACKEND_BASE_URL = "http://localhost:8080";
    private static final String MILESTONE_ENDPOINT = BACKEND_BASE_URL + "/milestones";

    @FXML
    private void initialize() {
        milestoneListView.setItems(milestones);
        loadMilestonesFromFile();
    }

    @FXML
    private void addMilestone() {
        String title = milestoneField.getText();

        if (title != null && !title.isEmpty()) {
            Milestone m = new Milestone(title);
            milestones.add(m);
            milestoneField.clear();
            saveMilestonesToFile();
            sendMilestoneToServer(m);   // ← slanje na web backend
        }
    }

    @FXML
    private void deleteMilestone() {
        Milestone selected =
                milestoneListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            milestones.remove(selected);
            saveMilestonesToFile();
            // Po želji: ovde možeš da dodaš i slanje DELETE zahteva na backend
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

    // ----------------- PERSISTENCIJA U FAJL -----------------

    private void loadMilestonesFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }
        try (Stream<String> lines =
                     Files.lines(STORAGE_PATH, StandardCharsets.UTF_8)) {

            List<String> titles = lines
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            titles.forEach(t -> milestones.add(new Milestone(t)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMilestonesToFile() {
        try {
            List<String> lines = milestones.stream()
                    .map(Milestone::getTitle)
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

    private void sendMilestoneToServer(Milestone milestone) {
        try {
            String title = milestone.getTitle();
            String jsonBody = "{\"title\":\"" + escapeJson(title) + "\"}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(MILESTONE_ENDPOINT))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        System.out.println("Milestone POST status: " + response.statusCode());
                        System.out.println("Response: " + response.body());
                    })
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        return null;
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String escapeJson(String s) {
        return s.replace("\"", "\\\"");
    }

    @FXML
    private void openMilestoneWebsite(ActionEvent event) {
        openWebsite("https://neziriis.github.io/SEPWeb/milestone.html");
    }

    private void openWebsite(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
