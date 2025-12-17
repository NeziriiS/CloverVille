package C;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {

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

    @FXML
    private void openRegisterActivities(ActionEvent event) {
        switchScene(event, "/RegisterActivities.fxml");
    }

    @FXML
    private void openResidents(ActionEvent event) {
        switchScene(event, "/Residents.fxml");
    }

    @FXML
    private void openGreenActivities(ActionEvent event) {
        switchScene(event, "/GreenActivities.fxml");
    }

    @FXML
    private void openTradeOffers(ActionEvent event) {
        switchScene(event, "/TradeOffers.fxml");
    }

    @FXML
    private void openCommunalTasks(ActionEvent event) {
        switchScene(event, "/CommunalTasks.fxml");
    }

    @FXML
    private void openMilestones(ActionEvent event) {
        switchScene(event, "/Milestones.fxml");
    }

    @FXML
    private void openBoost(ActionEvent event) {
        switchScene(event, "/Boost.fxml");
    }
}