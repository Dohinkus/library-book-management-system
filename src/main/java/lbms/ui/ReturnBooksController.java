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
        dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        returnedBooksTable.setItems(FXCollections.observableArrayList());

        // NEW: show all past returns by default
        loadAllReturned();
    }

    @FXML
    private void handleReturn() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String isbn = isbnField.getText() == null ? "" : isbnField.getText().trim();

        if (username.isEmpty() || isbn.isEmpty()) {
            showError("Please enter both username and ISBN.");
            return;
        }

        try {
            CheckOutDAO dao = new CheckOutDAO();

            // find an active checkout for this user + ISBN
            List<CheckOut> active = dao.getActiveCheckouts(username);
            CheckOut toReturn = active.stream()
                    .filter(co -> isbn.equals(co.getIsbn()))
                    .findFirst()
                    .orElse(null);

            if (toReturn == null) {
                showError("No active checkout found for this user and ISBN.");
                // Still show current returns so the table isn't blank
                loadReturnedForUserOrAll(username);
                return;
            }

            boolean ok = dao.returnBook(toReturn.getOrderId(), LocalDate.now().toString());
            if (ok) {
                showInfo("Book returned successfully.");
                // After returning, show returns for that user (or all if username cleared)
                loadReturnedForUserOrAll(username);
                isbnField.clear();
            } else {
                showError("Return operation failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error while returning book: " + e.getMessage());
        }
    }

    // NEW: view past returns without returning a book
    @FXML
    private void handleLoadReturns() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        loadReturnedForUserOrAll(username);
    }

    @FXML
    private void handleReturnHome() {
        switchScene("HomePageA.fxml");
    }

    private void loadAllReturned() {
        try {
            CheckOutDAO dao = new CheckOutDAO();
            List<CheckOut> list = dao.getAllReturned();
            returnedBooksTable.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load returned books: " + e.getMessage());
        }
    }

    private void loadReturnedForUserOrAll(String username) {
        try {
            CheckOutDAO dao = new CheckOutDAO();
            List<CheckOut> list;
            if (username == null || username.isBlank()) {
                list = dao.getAllReturned();
            } else {
                list = dao.getReturnedForUser(username);
            }
            returnedBooksTable.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load returned books: " + e.getMessage());
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
