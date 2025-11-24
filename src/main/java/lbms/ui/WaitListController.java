package lbms.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lbms.bo.AppAccount;
import lbms.bo.Book;
import lbms.bo.WaitListEntry;
import lbms.dao.AppAccountDAO;
import lbms.dao.BookDAO;
import lbms.dao.WaitListDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class WaitListController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField isbnField;

    @FXML
    private Button addButton;

    @FXML
    private Button RetrunHomeButton;

    @FXML
    private TableView<WaitListEntry> waitlistTable;

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

        waitlistTable.setItems(FXCollections.observableArrayList());

        // NEW: load all waitlist entries on page open
        loadAllWaitlist();
    }

    @FXML
    private void handleaddButton() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String isbn = isbnField.getText() == null ? "" : isbnField.getText().trim();

        if (username.isEmpty() || isbn.isEmpty()) {
            showError("Please enter both username and ISBN.");
            return;
        }

        try {
            // verify user exists
            AppAccountDAO accDao = new AppAccountDAO();
            AppAccount acc = accDao.getByUsername(username);
            if (acc == null) {
                showError("No such user in the system: " + username);
                return;
            }

            // verify book exists
            BookDAO bookDao = new BookDAO();
            Book book = bookDao.getByISBN(isbn);
            if (book == null) {
                showError("No such book with ISBN: " + isbn);
                return;
            }

            WaitListDAO dao = new WaitListDAO();
            boolean added = dao.addToWaitList(username, isbn);

            if (added) {
                showInfo("User added to waitlist.");
                // refresh list – show entries for this ISBN if provided, else all
                if (!isbn.isEmpty()) {
                    loadWaitlistForIsbn(isbn);
                } else {
                    loadAllWaitlist();
                }
                isbnField.clear();
            } else {
                showError("Could not add to waitlist (no rows inserted).");
            }

        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg != null && msg.toLowerCase().contains("duplicate")) {
                showError("That user is already on the waitlist for this book.");
            } else if (msg != null && msg.toLowerCase().contains("foreign key")) {
                showError("User or book not found (FK constraint). Please check username and ISBN.");
            } else {
                ex.printStackTrace();
                showError("Could not update waitlist: " + ex.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not update waitlist: " + e.getMessage());
        }
    }

    // Optional: wire this to a "Refresh" button or ISBN field onAction
    @FXML
    private void handleRefresh() {
        String isbn = isbnField.getText() == null ? "" : isbnField.getText().trim();
        if (isbn.isEmpty()) {
            loadAllWaitlist();
        } else {
            loadWaitlistForIsbn(isbn);
        }
    }

    private void loadAllWaitlist() {
        try {
            WaitListDAO dao = new WaitListDAO();
            List<WaitListEntry> entries = dao.getAll();
            waitlistTable.setItems(FXCollections.observableArrayList(entries));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Could not load waitlist: " + e.getMessage());
        }
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

    @FXML
    private void handleReturnHome() {
        switchScene("HomePageA.fxml");
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
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Waitlist");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
