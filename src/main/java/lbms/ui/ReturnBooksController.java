package lbms.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lbms.bo.CheckOut;
import lbms.dao.CheckOutDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReturnBooksController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField isbnField;

    @FXML
    private Button returnButton;

    @FXML
    private Button RetrunHomeButton;

    @FXML
    private TableView<CheckOut> returnedBooksTable;

    @FXML
    private TableColumn<CheckOut, String> UsernameColumn;

    @FXML
    private TableColumn<CheckOut, String> isbnColumn;

    @FXML
    private TableColumn<CheckOut, String> dateReturnedColumn;

    @FXML
    private void initialize() {
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        // For a simple demo, show the checkout date in this column (header text
        // still says "Date Returned", but that's okay for now).
        dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCheckedOut"));

        returnedBooksTable.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void handleReturn() {
        String username = usernameField.getText();
        String isbn = isbnField.getText();

        if (username == null || username.isBlank() || isbn == null || isbn.isBlank()) {
            showError("Please enter both username and ISBN.");
            return;
        }

        try {
            CheckOutDAO dao = new CheckOutDAO();
            // Get all active checkouts for this user
            List<CheckOut> active = dao.getActiveCheckouts(username);

            // Find the first checkout for the given ISBN
            CheckOut toReturn = active.stream()
                    .filter(co -> isbn.equals(co.getIsbn()))
                    .findFirst()
                    .orElse(null);

            if (toReturn == null) {
                showError("No active checkout found for that user and ISBN.");
                return;
            }

            boolean ok = dao.returnBook(toReturn.getOrderId(), LocalDate.now().toString());

            if (ok) {
                showInfo("Book returned successfully.");
                // Reload active checkouts (table shows remaining active loans)
                loadActiveCheckouts(username);
                isbnField.clear();
            } else {
                showError("Return operation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error while returning book: " + e.getMessage());
        }
    }

    @FXML
    private void handleReturnHome() {
        switchScene("HomePageA.fxml");
    }

    private void loadActiveCheckouts(String username) {
        try {
            CheckOutDAO dao = new CheckOutDAO();
            List<CheckOut> active = dao.getActiveCheckouts(username);
            returnedBooksTable.setItems(FXCollections.observableArrayList(active));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load active checkouts: " + e.getMessage());
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
            showError("Unable to load screen: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Return Book");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
