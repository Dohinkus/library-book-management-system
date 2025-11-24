package lbms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminHomeController {

    @FXML
    private Button ToSearch;

    @FXML
    private Button ToCheckout;

    // fx:id in HomePageA.fxml is ToWaitList
    @FXML
    private Button ToWaitList;

    @FXML
    private Button ToReturn;

    // helper to swap scenes
    private void switchScene(String fxmlName) {
        try {
            Stage stage = (Stage) ToSearch.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load screen");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // handlers to swap scenes

    @FXML
    private void handleToSearch() {
        switchScene("LibrarianSearch.fxml");
    }

    @FXML
    private void handleToCheckout() {
        switchScene("CheckOutPage.fxml");
    }

    @FXML
    private void handleToWaitlist() {
        switchScene("WaitList.fxml");
    }

    @FXML
    private void handleToReturn() {
        // note: FXML file is actually named RetrunBooks.fxml
        switchScene("RetrunBooks.fxml");
    }
}
