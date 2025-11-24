package lbms.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import lbms.bo.WaitListEntry;
import lbms.dao.WaitListDAO;

import java.io.IOException;
import java.util.List;

public class WaitListController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField isbnField;

    @FXML
    private Button addButton;

    // fx:id in WaitList.fxml is RetrunHomeButton
    @FXML
    private Button RetrunHomeButton;

    @FXML
    private TableView<WaitListEntry> waitlistTable;

    // fx:id in FXML is UsernameColumn
    @FXML
    private TableColumn<WaitListEntry, String> UsernameColumn;

    @FXML
    private TableColumn<WaitListEntry, String> isbnColumn;

    @FXML
    private TableColumn<WaitListEntry, String> datePlacedColumn;

    @FXML
    private void initialize() {
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        datePlacedColumn.setCellValueFactory(new PropertyValueFactory<>("datePlaced"));

        // starts with an empty list (no ISBN selected yet)
        waitlistTable.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void handleaddButton() {
        String username = usernameField.getText();
        String isbn = isbnField.getText();

        if (username == null || username.isBlank() || isbn == null || isbn.isBlank()) {
            showError("Please enter both username and ISBN");
            return;
        }

        try {
            WaitListDAO dao = new WaitListDAO();
            boolean added = dao.addToWaitList(username, isbn);

            if (added) {
                showInfo("User added to waitlist");
                // refresh table for this ISBN
                loadWaitlistForIsbn(isbn);
                isbnField.clear();
            } else {
                showError("Error adding to waitlist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not update waitlist: " + e.getMessage());
        }
    }

    @FXML
    private void handleReturnHome() {
        switchScene("HomePageA.fxml");
    }

    private void loadWaitlistForIsbn(String isbn) {
        try {
            WaitListDAO dao = new WaitListDAO();
            List<WaitListEntry> entries = dao.getWaitListForBook(isbn);
            waitlistTable.setItems(FXCollections.observableArrayList(entries));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load waitlist: " + e.getMessage());
        }
    }

    private void switchScene(String fxmlName) {
        try {
            Stage stage = (Stage) RetrunHomeButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Could not switch screen: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Waitlist");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
