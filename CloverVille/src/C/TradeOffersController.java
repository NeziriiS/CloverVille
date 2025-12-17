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

public class TradeOffersController {

    @FXML
    private TextField offerField;

    @FXML
    private ListView<TradeOffer> offerListView;

    private final ObservableList<TradeOffer> offers =
            FXCollections.observableArrayList();

    private static final Path STORAGE_PATH =
            Paths.get(System.getProperty("user.home"), "clover_trade_offers.txt");

    // URL backend servera – PROMENI po potrebi
    private static final String BACKEND_BASE_URL = "http://localhost:8080";
    private static final String TRADE_OFFER_ENDPOINT = BACKEND_BASE_URL + "/tradeoffers";

    @FXML
    private void initialize() {
        offerListView.setItems(offers);
        loadOffersFromFile();
    }

    @FXML
    private void addOffer() {
        String text = offerField.getText();

        if (text != null && !text.isEmpty()) {
            TradeOffer offer = new TradeOffer(text);
            offers.add(offer);
            offerField.clear();
            saveOffersToFile();
            sendTradeOfferToServer(offer);  // ← slanje na web backend
        }
    }

    @FXML
    private void deleteOffer() {
        TradeOffer selected =
                offerListView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            offers.remove(selected);
            saveOffersToFile();
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


    private void loadOffersFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }
        try (Stream<String> lines =
                     Files.lines(STORAGE_PATH, StandardCharsets.UTF_8)) {

            List<String> list = lines
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            list.forEach(t -> offers.add(new TradeOffer(t)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOffersToFile() {
        try {
            List<String> lines = offers.stream()
                    .map(TradeOffer::getText)
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


    private void sendTradeOfferToServer(TradeOffer offer) {
        try {
            String text = offer.getText();
            String jsonBody = "{\"text\":\"" + escapeJson(text) + "\"}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TRADE_OFFER_ENDPOINT))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        System.out.println("TradeOffer POST status: " + response.statusCode());
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
    private void openTradeOfferWebsite(ActionEvent event) {
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
