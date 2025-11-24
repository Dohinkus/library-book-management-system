package lbms.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lbms.bo.CheckOut;
import lbms.dao.BookDAO;
import lbms.dao.CheckOutDAO;
import lbms.dao.WaitListDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CheckOutPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField isbnField;

    @FXML
    private Button CheckAvaliabilityButton;

    @FXML
    private Label isAvaliableLabel;

    @FXML
    private Button ConfrimCheckOutButton;

    @FXML
    private Button RetrunHomeButton;

    @FXML
    private TableView<CheckOut> CheckoutTable;

    @FXML
    private TableColumn<CheckOut, String> isbnColumn;

    @FXML
    private TableColumn<CheckOut, String> dateCheckOutColumn;

    @FXML
    private TableColumn<CheckOut, String> dateReturnedColumn;

    @FXML
    private void initialize() {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        dateCheckOutColumn.setCellValueFactory(new PropertyValueFactory<>("dateCheckedOut"));
        dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        CheckoutTable.setItems(FXCollections.observableArrayList());

        // NEW: show all active checkouts by default
        loadAllActiveCheckouts();
    }

    // Called when you press "Check Availability"
    @FXML
    private void handleCheckAvaliability() {
        String isbn = isbnField.getText();

        if (isbn == null || isbn.isBlank()) {
            showError("Please enter an ISBN");
            return;
        }

        try {
            BookDAO bookDAO = new BookDAO();
            int available = bookDAO.getAvailableCopies(isbn);

            if (available > 0) {
                isAvaliableLabel.setText("Available copies: " + available);
            } else {
                isAvaliableLabel.setText("No copies available");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error while checking availability: " + e.getMessage());
        }
    }

    // Called when you press "Confirm Checkout"
    @FXML
    private void handleConfrimCheckOut() {
        String username = usernameField.getText();
        String isbn = isbnField.getText();

        if (username == null || username.isBlank() || isbn == null || isbn.isBlank()) {
            showError("Please enter both username and ISBN");
            return;
        }

        try {
            BookDAO bookDAO = new BookDAO();
            int available = bookDAO.getAvailableCopies(isbn);

            if (available <= 0) {
                // add to waitlist instead
                WaitListDAO waitListDAO = new WaitListDAO();
                boolean added = waitListDAO.addToWaitList(username.trim(), isbn.trim());
                if (added) {
                    isAvaliableLabel.setText("No copies available. User added to waitlist.");
                    showInfo("No copies available. User added to waitlist.");
                } else {
                    showError("Couldn't add to waitlist");
                }
                return;
            }

            // build the checkout BO
            CheckOut co = new CheckOut();
            co.setUsername(username.trim());
            co.setIsbn(isbn.trim());
            co.setDateCheckedOut(LocalDate.now().toString());

            CheckOutDAO dao = new CheckOutDAO();
            int orderId = dao.insert(co);

            if (orderId > 0) {
                showInfo("Checkout successful. Order ID: " + orderId);
                isAvaliableLabel.setText("");
                isbnField.clear();

                // NEW: refresh active checkouts for this user
                loadCheckoutsForUser(username.trim());
            } else {
                showError("Checkout failed. Order ID not generated.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error during checkout: " + e.getMessage());
        }
    }

    // NEW: view active checkouts without doing a checkout
    // You can wire this to a button OR to usernameField's onAction
    @FXML
    private void handleLoadUserCheckouts() {
        String username = usernameField.getText();
        if (username == null || username.isBlank()) {
            // If no username, show all active checkouts
            loadAllActiveCheckouts();
        } else {
            loadCheckoutsForUser(username.trim());
        }
    }

    @FXML
    private void handleReturnHome() {
        switchScene("HomePageA.fxml");
    }

    private void loadAllActiveCheckouts() {
        try {
            CheckOutDAO dao = new CheckOutDAO();
            List<CheckOut> checkouts = dao.getAllActiveCheckouts();
            CheckoutTable.setItems(FXCollections.observableArrayList(checkouts));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load active checkouts: " + e.getMessage());
        }
    }

    private void loadCheckoutsForUser(String username) {
        try {
            CheckOutDAO dao = new CheckOutDAO();
            List<CheckOut> checkouts = dao.getActiveCheckouts(username);
            CheckoutTable.setItems(FXCollections.observableArrayList(checkouts));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load checkouts for user: " + e.getMessage());
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
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Checkout");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
